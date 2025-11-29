package br.com.ifsp.backend.service.catalog;

import br.com.ifsp.backend.dto.request.create.CreateLabelRequestDTO;
import br.com.ifsp.backend.dto.request.patch.UpdateLabelRequestDTO;
import br.com.ifsp.backend.exceptions.ResourceNotFoundException;
import br.com.ifsp.backend.model.catalog.Label;
import br.com.ifsp.backend.repository.catalog.LabelRepository;
import br.com.ifsp.backend.repository.catalog.ReleaseLabelRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LabelService {

    private final LabelRepository labelRepository;
    private final ReleaseLabelRepository releaseLabelRepository;

    public LabelService(LabelRepository labelRepository, ReleaseLabelRepository releaseLabelRepository) {
        this.labelRepository = labelRepository;
        this.releaseLabelRepository = releaseLabelRepository;
    }

    public Label createLabel(CreateLabelRequestDTO data) {
        var newLabel = new Label();

        newLabel.setName(data.name());
        newLabel.setFoundationDate( data.foundationDate());
        newLabel.setEndDate(data.endDate());
        newLabel.setBio(data.bio());

        labelRepository.save(newLabel);

        return newLabel;
    }

    public List<Label> listAll() {
        return labelRepository.findAll();
    }

    public Label findById(Long id) {
        return labelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nenhuma gravadora encontrada com o ID: " + id));
    }

    @Transactional
    public Label update(Long id, UpdateLabelRequestDTO data) {
        // 1. Busca a gravadora (Reutiliza o teu método findById que lança exceção)
        Label label = findById(id);

        // 2. Atualização condicional (Partial Update)
        if (data.name() != null && !data.name().isBlank()) {
            label.setName(data.name());
        }

        if (data.foundationDate() != null) {
            label.setFoundationDate(data.foundationDate());
        }

        // Nota: endDate pode ser atualizado, mesmo que seja para definir como null (se a gravadora voltar à atividade?)
        // Se quiseres permitir "apagar" a data, terias de usar uma lógica diferente,
        // mas aqui assumimos que se enviar data, atualiza.
        if (data.endDate() != null) {
            label.setEndDate(data.endDate());
        }

        if (data.bio() != null) {
            label.setBio(data.bio());
        }

        // 3. Salva e retorna
        return labelRepository.save(label);
    }

    public Page<Label> findAll(String name, Pageable pageable) {
        if (name != null && !name.isBlank()) {
            return labelRepository.findByNameContainingIgnoreCase(name, pageable);
        }
        return labelRepository.findAll(pageable);
    }

    @Transactional
    public void delete(Long id) {
        // 1. Busca a Label (para garantir que existe)
        Label label = findById(id);

        // 2. REGRA DE INTEGRIDADE: Existem discos lançados por ela?
        if (releaseLabelRepository.existsByLabelId(id)) {
            throw new IllegalArgumentException("Não é possível excluir esta Gravadora pois ela possui Discos (Releases) vinculados.");
        }

        // 3. Deleta
        labelRepository.delete(label);
    }
}
