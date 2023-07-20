package by.tupik.backend.BackendDiplom.controllers;

import by.tupik.backend.BackendDiplom.dto.PlayersResponse;
import by.tupik.backend.BackendDiplom.dto.PlayerDTO;
import by.tupik.backend.BackendDiplom.models.Player;
import by.tupik.backend.BackendDiplom.servicies.PlayersService;
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
@RequestMapping("/api/players")
public class PlayersController {
    private final PlayersService playersService;
    private final MappingUtils mappingUtils;

    @Autowired
    public PlayersController(PlayersService playersService, MappingUtils mappingUtils) {
        this.playersService = playersService;
        this.mappingUtils = mappingUtils;
    }

    @PostMapping()
    public Player createPlayer(@RequestBody @Valid Player player, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            System.out.println(bindingResult.toString());
            return null;
        }

        playersService.create(player);

        return player;
    }

    @GetMapping()
    public PlayersResponse getPlayers() {
        List<Player> players = playersService.findAll();
        List<PlayerDTO> playerDTOs = new ArrayList<>();

        for (Player player : players) {
            playerDTOs.add(mappingUtils.convertToPlayerDTO(player));
        }

        return new PlayersResponse(playerDTOs);
    }

    @GetMapping("/{id}")
    public PlayerDTO getPlayer(@PathVariable("id") int id) {
        return mappingUtils.convertToPlayerDTO(playersService.findOne(id));
    }

    @PutMapping("/{id}")
    public Player updatePlayer(@PathVariable("id") int id, @RequestBody @Valid Player updatedPlayer, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return null;
        }

        playersService.update(id, updatedPlayer);

        return updatedPlayer;
    }

    @DeleteMapping("/{id}")
    public int deletePlayer(@PathVariable("id") int id){
        playersService.delete(id);
        return id;
    }
}
