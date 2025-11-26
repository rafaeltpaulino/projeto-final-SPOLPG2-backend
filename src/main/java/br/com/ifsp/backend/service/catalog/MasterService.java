package br.com.ifsp.backend.service.catalog;

import br.com.ifsp.backend.repository.catalog.MasterRepository;
import org.springframework.stereotype.Service;

@Service
public class MasterService {

    private final MasterRepository masterRepository;

    public MasterService(MasterRepository masterRepository) {
        this.masterRepository = masterRepository;
    }


}
