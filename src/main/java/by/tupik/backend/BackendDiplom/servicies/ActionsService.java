package by.tupik.backend.BackendDiplom.servicies;

import by.tupik.backend.BackendDiplom.models.Action;
import by.tupik.backend.BackendDiplom.repositories.ActionsRepositry;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActionsService {
    private final ActionsRepositry actionsRepository;

    @Autowired
    public ActionsService(ActionsRepositry actionsRepository) {
        this.actionsRepository = actionsRepository;
    }

    @Transactional
    public void create(Action action){
        actionsRepository.save(action);
    }

    public List<Action> findAll() {
        return actionsRepository.findAll();
    }

    public Action findOne(int id) {
        return actionsRepository.findById(id).orElse(null);
    }

    @Transactional
    public void update(int id, Action updatedAction){
        updatedAction.setId(id);
        actionsRepository.save(updatedAction);
    }

    @Transactional
    public void delete(int id){
        actionsRepository.deleteById(id);
    }
}
