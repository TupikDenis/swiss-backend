package by.tupik.backend.BackendDiplom.servicies;

import by.tupik.backend.BackendDiplom.models.Player;
import by.tupik.backend.BackendDiplom.models.Tournament;
import by.tupik.backend.BackendDiplom.repositories.PlayersRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayersService {
    private final PlayersRepository playersRepository;
    private final TournamentsService tournamentsService;
    private final MatchesService matchesService;

    @Autowired
    public PlayersService(PlayersRepository playersRepository, TournamentsService tournamentsService, MatchesService matchesService) {
        this.playersRepository = playersRepository;
        this.tournamentsService = tournamentsService;
        this.matchesService = matchesService;
    }

    @Transactional
    public void create(Player player){
        playersRepository.save(player);
    }

    public List<Player> findAll() {
        return playersRepository.findAll();
    }

    public Player findOne(int id) {
        return playersRepository.findById(id).orElse(null);
    }

    @Transactional
    public void update(int id, Player updatedPlayer){
        updatedPlayer.setId(id);
        playersRepository.save(updatedPlayer);
    }

    @Transactional
    public void delete(int id){
        playersRepository.deleteById(id);
    }
}
