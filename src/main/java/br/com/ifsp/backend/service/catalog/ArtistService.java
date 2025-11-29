package br.com.ifsp.backend.service.catalog;

import br.com.ifsp.backend.dto.request.create.CreateArtistRequestDTO;
import br.com.ifsp.backend.dto.request.create.CreateGroupRequestDTO;
import br.com.ifsp.backend.dto.request.create.CreatePersonRequestDTO;
import br.com.ifsp.backend.dto.request.create.MemberBindingDTO;
import br.com.ifsp.backend.dto.request.patch.PatchArtistRequestDTO;
import br.com.ifsp.backend.exceptions.ResourceNotFoundException;
import br.com.ifsp.backend.model.catalog.*;
import br.com.ifsp.backend.model.Country;
import br.com.ifsp.backend.repository.catalog.ArtistRepository;
import br.com.ifsp.backend.repository.CountryRepository;
import br.com.ifsp.backend.service.CountryService;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ArtistService {

    private final ArtistRepository artistRepository;
    private final CountryRepository countryRepository;
    private final CountryService countryService;

    public ArtistService(CountryRepository countryRepository, ArtistRepository artistRepository, CountryService countryService){
        this.countryRepository = countryRepository;
        this.artistRepository = artistRepository;
        this.countryService = countryService;
    }

    public Artist createPerson(CreatePersonRequestDTO data) {
        PersonArtist person = new PersonArtist();

        // 1. Dados Específicos
        person.setBirthDate(data.birthDate());
        person.setDeathDate(data.deathDate());

        // 2. Dados Comuns (Passando valores individuais)
        // Nota: Se o DTO não tem imageUrl, passe null ou adicione ao DTO
        fillCommonData(person, data.name(), data.description(), data.imageUrl(), data.countryId());

        return artistRepository.save(person);
    }

    @Transactional
    public Artist createGroup(CreateGroupRequestDTO data) {
        GroupArtist group = new GroupArtist();

        // 1. Dados Específicos
        group.setFormationDate(data.formationDate());
        group.setEndDate(data.endDate());

        // 2. Dados Comuns
        fillCommonData(group, data.name(), data.description(), data.imageUrl(), data.countryId());

        // 3. Salva a banda primeiro (para gerar o ID e poder vincular membros)
        group = artistRepository.save(group);

        // 4. Lógica de Membros (Somente existentes)
        if (data.members() != null && !data.members().isEmpty()) {

            for (MemberBindingDTO memberDto : data.members()) {

                // PASSO 1: Busca no banco (Obrigatório existir)
                Artist foundArtist = findById(memberDto.personArtistId());

                // PASSO 2: Validação de Tipo (Regra de Ouro)
                // Usamos Pattern Matching do Java 16+ ("instanceof PersonArtist member")
                // Isso checa o tipo e já faz o cast para a variável 'member'
                if (!(foundArtist instanceof PersonArtist member)) {
                    throw new IllegalArgumentException("O ID " + memberDto.personArtistId() + " pertence a um Grupo. Apenas artistas do tipo PESSOA podem ser membros.");
                }

                // PASSO 3: Cria o Vínculo
                GroupMember link = new GroupMember();

                link.setGroup(group);
                link.setMember(member); // Aqui passamos o objeto PersonArtist validado
                link.setRole(memberDto.role());
                link.setJoinDate(memberDto.joinDate());
                link.setActive(memberDto.active());

                // Configura Chave Composta
                GroupMemberId linkId = new GroupMemberId();
                linkId.setGroupId(group.getId());
                linkId.setMemberId(member.getId());
                link.setId(linkId);

                // Adiciona na lista para o Cascade salvar
                group.getMembers().add(link);
            }

            // Salva a banda com os novos membros vinculados
            group = artistRepository.save(group);
        }

        return group;
    }

    private void fillCommonData(Artist artist, String name, String description, String imageUrl, Long countryId) {
        artist.setName(name);
        artist.setDescription(description);
        artist.setImageUrl(imageUrl);

        if (countryId != null) {
            Country country = countryService.findById(countryId);
            artist.setCountry(country);
        }
    }

    public List<Artist> listAll() {
        return artistRepository.findAll();
    }

    // 1. Buscar por ID (Usado na página de detalhes)
    @Transactional() // readOnly melhora performance no banco
    public Artist findById(Long id) {
        Artist artist = artistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Artista não encontrado: " + id));

        // "Cutuca" a lista para carregar os membros enquanto a transação está aberta
        if (artist instanceof GroupArtist group) {
            group.getMembers().size();
        }

        return artist;
    }

    // 2. Listar Todos (Com paginação e filtro opcional por nome)
    @Transactional()
    public Page<Artist> findAll(Pageable pageable, String name) {
        if (name != null && !name.isBlank()) {
            return artistRepository.findByNameContainingIgnoreCase(name, pageable);
        }
        return artistRepository.findAll(pageable);
    }

    public List<Artist> findAllById(Set<Long> artistsId) {
        return artistRepository.findAllById(artistsId);
    }

    @Transactional
    public void delete(Long id) {
        if (!artistRepository.existsById(id)) {
            throw new ResourceNotFoundException("Nenhum artista encontrado com ID: " + id);
        }

        try {
            artistRepository.deleteById(id);
            artistRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Não é possível excluir este artista pois ele possui Obras (Masters) associadas.");
        }
    }
}
