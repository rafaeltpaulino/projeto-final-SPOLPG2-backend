package br.com.ifsp.backend.service.catalog;

import br.com.ifsp.backend.dto.request.CreateLabelRequestDTO;
import br.com.ifsp.backend.exceptions.ResourceNotFoundException;
import br.com.ifsp.backend.model.catalog.Label;
import br.com.ifsp.backend.repository.catalog.LabelRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LabelService {

    private final LabelRepository labelRepository;

    public LabelService(LabelRepository labelRepository) {
        this.labelRepository = labelRepository;
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
}
