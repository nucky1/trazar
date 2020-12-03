package ar.edu.unsl.trazar.service;

import ar.edu.unsl.trazar.repository.LocalRepository;
import ar.edu.unsl.trazar.entity.Local;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LocalServiceImpl implements LocalService {

    @Resource
    private LocalRepository localRepository;

    @Override
    public Local createLocal(Local local) {
        return localRepository.save(local);
    }

    @Override
    public Local getLocalById(Integer id) {
        return localRepository.findById(id).orElse(null);
    }

    @Override
    public List<Local> getLocales() {
        return localRepository.findAll();
    }
}
