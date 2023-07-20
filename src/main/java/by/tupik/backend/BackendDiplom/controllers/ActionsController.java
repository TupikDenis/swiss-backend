package by.tupik.backend.BackendDiplom.controllers;

import by.tupik.backend.BackendDiplom.dto.ActionDTO;
import by.tupik.backend.BackendDiplom.dto.ActionsResponse;
import by.tupik.backend.BackendDiplom.dto.TournamentDTO;
import by.tupik.backend.BackendDiplom.models.Action;
import by.tupik.backend.BackendDiplom.models.Tournament;
import by.tupik.backend.BackendDiplom.servicies.ActionsService;
import by.tupik.backend.BackendDiplom.utils.MappingUtils;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/actions")
public class ActionsController {
    private final ActionsService actionsService;
    private final ModelMapper modelMapper;
    private final MappingUtils mappingUtils;

    @Autowired
    public ActionsController(ActionsService actionsService, ModelMapper modelMapper, MappingUtils mappingUtils) {
        this.actionsService = actionsService;
        this.modelMapper = modelMapper;
        this.mappingUtils = mappingUtils;
    }

    @PostMapping()
    public Action createActions(@RequestBody @Valid Action action, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            System.out.println(bindingResult.toString());
            return null;
        }

        actionsService.create(action);

        return action;
    }

    @GetMapping()
    public ActionsResponse getAllActions(){
        List<Action> actions = actionsService.findAll();
        List<ActionDTO> actionDTOs = new ArrayList<>();

        for (Action action : actions) {
            actionDTOs.add(mappingUtils.convertToActionsDTO(action));
        }
        
        return new ActionsResponse(actionDTOs);
        //return new ActionsResponse(actionsService.findAll().stream().map(this::convertToActionsDTO).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ActionDTO getActions(@PathVariable int id){
        return mappingUtils.convertToActionsDTO(actionsService.findOne(id));
    }

    @PutMapping("/{id}")
    public Action updateActions(@PathVariable int id, @RequestBody @Valid Action updatedAction, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            System.out.println(bindingResult.toString());
            return null;
        }

        actionsService.update(id, updatedAction);

        return updatedAction;
    }

    @DeleteMapping("/{id}")
    public int deleteActions(@PathVariable int id){
        actionsService.delete(id);
        return id;
    }
}
