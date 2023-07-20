package by.tupik.backend.BackendDiplom.algorithms.Swiss;

import by.tupik.backend.BackendDiplom.dto.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public class Swiss {
    private final static Logger LOGGER = Logger.getLogger(Swiss.class.getName());

    public static RoundMatching pairNextRound(MatchingRule firstRoundMatchingRule,
                                              List<PlayerDTO> players,
                                              int rounds, int currentRound, TournamentDTO tournament) {
        currentRound++;

        if (currentRound > rounds) {
            throw new IllegalArgumentException("Tournament ended after " + rounds + " rounds");
        }

        if (currentRound == 1) {
            // first round matching
            if (firstRoundMatchingRule == MatchingRule.MATCH_ORDERED) {
                return firstRoundOrderedMatching(players, tournament);

            }
            if (firstRoundMatchingRule == MatchingRule.MATCH_RANDOM) {
                return firstRoundRandomMatching(players, tournament);

            }
            throw new IllegalArgumentException("not supported yet");
        }

        // need to pair new round
        RoundMatching matching = getNextRoundMatching(players, currentRound, tournament);
        if(players.size() / 2 != matching.getMatches().size()){
            throw new IllegalArgumentException("Can't do pair :( ");
        }

        return matching;
    }

    private static RoundMatching firstRoundOrderedMatching(List<PlayerDTO> players, TournamentDTO tournament) {
        final int currentRound = 1;
        RoundMatching matching = new RoundMatching(currentRound);
        int index = 0;
        List<MatchDTO> allMatches = new ArrayList<>();

        while (index < players.size() / 2) {
            MatchDTO match;

            if(index % 2 == 0){
                match = new MatchDTO(currentRound, tournament, players.get(index), MatchResult.NO_RESULT, players.get(players.size() / 2 + index));
            } else {
                match = new MatchDTO(currentRound, tournament, players.get(players.size() / 2 + index), MatchResult.NO_RESULT, players.get(index));
            }

            allMatches.add(match);
            matching.addMatch(match.getWhitePlayer(), match.getBlackPlayer(), tournament);
            match.getWhitePlayer().setPreviousColors(Color.WHITE, 0);
            match.getBlackPlayer().setPreviousColors(Color.BLACK, 0);
            match.getWhitePlayer().addGame();
            match.getBlackPlayer().addGame();
            index++;
        }

        return matching;
    }

    private static RoundMatching firstRoundRandomMatching(List<PlayerDTO> players, TournamentDTO tournament) {
        final int currentRound = 1;
        RoundMatching matching = new RoundMatching(currentRound);
        List<PlayerDTO> notPairedYet = new ArrayList<>(players);
        RandomWrapper random = new RandomWrapper();
        List<MatchDTO> allMatches = new ArrayList<>();

        while (notPairedYet.size() > 1) {
            int player1Index = random.nextInt(notPairedYet.size());
            PlayerDTO player1 = notPairedYet.get(player1Index);
            notPairedYet.remove(player1Index);
            int player2Index = random.nextInt(notPairedYet.size());
            PlayerDTO player2 = notPairedYet.get(player2Index);
            notPairedYet.remove(player2Index);
            MatchDTO match = new MatchDTO(currentRound, tournament, player1, MatchResult.NO_RESULT, player2);
            allMatches.add(match);
            matching.addMatch(match.getWhitePlayer(), match.getBlackPlayer(), tournament);
            match.getWhitePlayer().setPreviousColors(Color.WHITE, 0);
            match.getBlackPlayer().setPreviousColors(Color.BLACK, 0);
            match.getWhitePlayer().addGame();
            match.getBlackPlayer().addGame();
        }

        return matching;
    }

    private static RoundMatching getNextRoundMatching(List<PlayerDTO> players, int currentRound, TournamentDTO tournament) {
        // sort the players based on their score
        List<PlayerDTO> sortedPlayers = new ArrayList<>(players);
        Collections.sort(sortedPlayers);

        List<PlayerDTO> unPairedPlayers = new ArrayList<PlayerDTO>(players);
        Collections.sort(unPairedPlayers);

        int byePlayerIndex = -1;
        RoundMatching newMatching = new RoundMatching(currentRound);

        if ((players.size() % 2) == 1) {
            // choose a bye player
            int index = players.size()  - 1;
            while (index >= 0) {
                PlayerDTO player = sortedPlayers.get(index);
                if (player.getByeRound() == 0) {
                    player.setByeRound(currentRound);
                    byePlayerIndex = index;
                    LOGGER.fine("player " + player + " bye round " + currentRound);
                    break;
                }
                index--;
            }
        }

        int groups = currentRound * 2 - 1;
        List<List<PlayerDTO>> listPlayer = new ArrayList<>(groups);

        for (int j = 0; j < groups; j++)  {
            listPlayer.add(new ArrayList<>());
        }

        for(int j = 0; j < groups; j++){
            double groupScore = 0.5 * j;
            for(int k = 0; k < players.size(); k++){
                if(groupScore == players.get(k).getScore()){
                    listPlayer.get(j).add(players.get(k));
                }
            }
        }

        for(int j = groups - 1; j >= 0; j--){
            if (listPlayer.get(j).size() % 2 != 0){
                listPlayer.get(j-1).add(0, listPlayer.get(j).get(listPlayer.get(j).size() - 1));
                listPlayer.get(j).remove(listPlayer.get(j).size() - 1);
            }
        }

        for(int j = 0; j < groups; j++){
            if(listPlayer.get(j).size() != 0){

                if(j + 1 == groups && !listPlayer.get(j).isEmpty()){
                    break;
                }

                for(int k = 0; k < (listPlayer.get(j).size() + 2) / 2 - 2; k++){
                    PlayerDTO player = listPlayer.get(j).remove(listPlayer.get(j).size() - 1);
                    listPlayer.get(j).add(1, player);
                }
            }
        }

        int count = 0;
        // iterate over the players, find a matching for the top player
        List<MatchDTO> allMatches = new ArrayList<>();
        for (int i = 0; i < players.size() ; i++) {
            int bestPlayerId;
            PlayerDTO bestScorePlayer;

            bestScorePlayer = sortedPlayers.get(i);
            bestPlayerId = bestScorePlayer.getPlace();

            if (bestPlayerId == byePlayerIndex) {
                LOGGER.fine("player " + bestScorePlayer + " bye this round");
                continue;
            }

            // check if this player is already scheduled this round
            if (newMatching.hasMatchForPlayer(bestScorePlayer)) {
                LOGGER.fine("round " + currentRound + " player " + bestScorePlayer + " already scheduled");
                continue;
            }

            boolean matchForBestPlayerFound = false;

            for(int j = listPlayer.size() - 1; j >= 0; j--){
                for(int k = 0; k < listPlayer.get(j).size() / 2; k++) {
                    PlayerDTO opponent = listPlayer.get(j).get((listPlayer.get(j).size() / 2 + k));

                    if (bestScorePlayer.equals(opponent)) {
                        continue;
                    }

                    int opponentIndex = opponent.getPlace();

                    if (opponentIndex == byePlayerIndex) {
                        LOGGER.fine(opponent + " bye this round");
                        continue;
                    }

                    if (newMatching.hasMatchForPlayer(opponent)) {
                        LOGGER.fine("round " + currentRound + " player " + opponent + " already scheduled");
                        continue;
                    }

                    if (listContainsMatchBetweenPlayers(allMatches, bestScorePlayer, opponent)) {
                        // already played. find next opp
                        LOGGER.fine("round " + currentRound + " game " + bestScorePlayer + " - " + opponent + " exists");
                        continue;
                    }


                    boolean isCreatedPair = createPair(allMatches, bestScorePlayer, opponent, newMatching, j, currentRound, tournament);
                    if (!isCreatedPair) {
                        continue;
                    }

                    unPairedPlayers.remove(bestScorePlayer);
                    unPairedPlayers.remove(opponent);
                    matchForBestPlayerFound = true;
                    break;
                }

                if (matchForBestPlayerFound) {
                    // ok ! continue on to next player
                    break;
                }
            }

            if (matchForBestPlayerFound) {
                // ok ! continue on to next player
                continue;
            }

            if (newMatching.getMatches().size() == (players.size()  / 2)) {
                // we have all games that we need
                continue;
            }

            // no match for the best player found. we now have to find a couple to break,
            // and opp for this player that will satisfy all conditions
            // so iterate on the pairing so far in reverse order
            LOGGER.fine("round " + currentRound + " need to switch pairs for " + bestScorePlayer
                    + " we have " + newMatching.getMatches().size() + " games");

            for (int g = newMatching.getMatches().size() - 1; g >= 0; g--) {
                MatchDTO pairedGame = newMatching.getMatches().get(g);
                // see if the best player can be matched vs any of this couple
                PlayerDTO player1 = pairedGame.getWhitePlayer();
                PlayerDTO player2 = pairedGame.getBlackPlayer();

                if ((listContainsMatchBetweenPlayers(allMatches, bestScorePlayer, player1)) &&
                        (listContainsMatchBetweenPlayers(allMatches, bestScorePlayer, player2))) {
                    // we can't use this pair because the best score user already played vs both of them
                    continue;
                }

                // ok have a candidate pairing. lets iterate over the players again from the bottom to find someone
                // to switch pairs with

                PlayerDTO switchPlayer;

                for (int p = players.size()  - 1; p >= 0; p--) {

                    switchPlayer = players.get(p);

                    // check that the switch player is not scheduled, and that it is not the bye user, or the best
                    // score user, or the chosen pairs wid,bid
                    if (newMatching.hasMatchForPlayer(switchPlayer)) {
                        LOGGER.fine("round " + currentRound + " switch user " + switchPlayer + " already scheduled");
                        continue;
                    }

                    if ((switchPlayer.equals(bestScorePlayer)) || (switchPlayer.getPlace() == byePlayerIndex) ||
                            (switchPlayer.equals(player1)) || (switchPlayer.equals(player2))) {
                        LOGGER.fine("round " + currentRound + " switch user " + switchPlayer + " is either the best score, bye, wid or bid");
                        continue;
                    }

                    LOGGER.fine("round " + currentRound + " candidate switch player " + switchPlayer);

                    // ok ! the last thing to check it that it is possible to make some pairing switch

                    if (!((listContainsMatchBetweenPlayers(allMatches, player1, switchPlayer)) ||
                            (listContainsMatchBetweenPlayers(allMatches, player2, bestScorePlayer)))) {
                        // we can switch. wid vs the switch user, the best player vs bid
                        LOGGER.fine("pairing remove game " + pairedGame);

                        if (!newMatching.removeMatchWithPlayer(player1)) {
                            LOGGER.warning("could not remove game with " + player1);
                            continue;
                            //return null;
                        }

                        unPairedPlayers.add(player1);
                        unPairedPlayers.add(player2);

                        boolean isCreatedPair = createPair(allMatches, player1, switchPlayer, newMatching, g, currentRound, tournament);
                        if(!isCreatedPair){
                            continue;
                        }

                        unPairedPlayers.remove(player1);
                        unPairedPlayers.remove(switchPlayer);

                        isCreatedPair = createPair(allMatches, player2, bestScorePlayer, newMatching, g, currentRound, tournament);
                        if(!isCreatedPair){
                            continue;
                        }

                        unPairedPlayers.remove(player2);
                        unPairedPlayers.remove(bestScorePlayer);

                        matchForBestPlayerFound = true;
                        break;
                    }

                    if (!((listContainsMatchBetweenPlayers(allMatches, player2, switchPlayer)) ||
                            (listContainsMatchBetweenPlayers(allMatches, player1, bestScorePlayer)))) {
                        // we can switch. wid vs the switch user,best player vs bid
                        LOGGER.fine("pairing remove game " + pairedGame);


                        if (!newMatching.removeMatchWithPlayer(player1)) {
                            LOGGER.warning("could not remove match with " + player1);
                            continue;
                            //return null;
                        }

                        unPairedPlayers.add(player1);
                        unPairedPlayers.add(player2);

                        boolean isCreatedPair = createPair(allMatches, player2, switchPlayer, newMatching, g, currentRound, tournament);
                        if(!isCreatedPair){
                            continue;
                        }

                        unPairedPlayers.remove(player2);
                        unPairedPlayers.remove(switchPlayer);

                        isCreatedPair = createPair(allMatches, player1, bestScorePlayer, newMatching, g, currentRound, tournament);
                        if(!isCreatedPair){
                            continue;
                        }

                        unPairedPlayers.remove(player1);
                        unPairedPlayers.remove(bestScorePlayer);

                        matchForBestPlayerFound = true;
                        break;
                    }
                }

                if (matchForBestPlayerFound) {
                    break;
                }
            }

            if (!matchForBestPlayerFound) {
                // TODO: Crazy pairing
                // TODO: 1. Get list unpaired players
                // TODO: 2. Look opponents for them
                // TODO: 3. If not, unpaired last match if it possible
                // TODO: 4. If can not unpaired,


                //newMatching.removeLastMatches(count);
                //i = -1;



                //LOGGER.warning("could not match all players. not enough players ?" + unPairedPlayers);
            }
        }

        /*if(newMatching.getMatches().size() != numberOfPlayers / 2){
            i = 0;
        }*/

        return newMatching;
    }

    private static boolean listContainsMatchBetweenPlayers(List<MatchDTO> matches, PlayerDTO player1, PlayerDTO player2) {
        for (MatchDTO match : matches) {
            if (match.hasPlayers(player1, player2)) {
                return true;
            }
        }
        return false;
    }

    private static boolean createPair(List<MatchDTO> allMatches, PlayerDTO player1, PlayerDTO player2, RoundMatching roundMatching, int pairNumber, int currentRound, TournamentDTO tournament){
        MatchDTO match;

        if(currentRound > 2){
            if(player1.getPreviousColors().get(player1.getPreviousColors().size() - 1) == Color.BLACK &&
                    player2.getPreviousColors().get(player2.getPreviousColors().size() - 1) == Color.WHITE){
                match = roundMatching.addMatch(player1, player2, tournament);
                allMatches.add(match);
                player1.setPreviousColors(Color.WHITE, 1);
                player2.setPreviousColors(Color.BLACK, 1);
            } else if (player2.getPreviousColors().get(player2.getPreviousColors().size() - 1) == Color.BLACK &&
                    player1.getPreviousColors().get(player1.getPreviousColors().size() - 1) == Color.WHITE){
                match = roundMatching.addMatch(player2, player1, tournament);
                allMatches.add(match);
                player1.setPreviousColors(Color.BLACK, 1);
                player2.setPreviousColors(Color.WHITE, 1);
            } else if(player1.getPreviousColors().get(player1.getPreviousColors().size() - 1) == Color.BLACK &&
                    player2.getPreviousColors().get(player2.getPreviousColors().size() - 1) == Color.BLACK) {
                if (player1.getPreviousColors().get(player1.getPreviousColors().size() - 2) == Color.BLACK &&
                        player2.getPreviousColors().get(player2.getPreviousColors().size() - 2) == Color.WHITE) {
                    match = roundMatching.addMatch(player1, player2, tournament);
                    allMatches.add(match);
                    player1.setPreviousColors(Color.WHITE, 1);
                    player2.setPreviousColors(Color.BLACK, 1);
                } else if (player2.getPreviousColors().get(player2.getPreviousColors().size() - 2) == Color.BLACK &&
                        player1.getPreviousColors().get(player1.getPreviousColors().size() - 2) == Color.WHITE) {
                    match = roundMatching.addMatch(player2, player1, tournament);
                    allMatches.add(match);
                    player1.setPreviousColors(Color.BLACK, 1);
                    player2.setPreviousColors(Color.WHITE, 1);
                } else if (player1.getPreviousColors().get(player1.getPreviousColors().size() - 2) == Color.BLACK &&
                        player2.getPreviousColors().get(player2.getPreviousColors().size() - 2) == Color.BLACK) {
                    //System.out.println("Players 2 black color in a row:" + player1.getName() + " " + player2.getName());
                    return false;
                } else {
                    match = roundMatching.addMatch(player1, player2, tournament);
                    allMatches.add(match);
                    player1.setPreviousColors(Color.WHITE, 1);
                    player2.setPreviousColors(Color.BLACK, 1);
                }

            } else if(player1.getPreviousColors().get(player1.getPreviousColors().size() - 1) == Color.WHITE &&
                    player2.getPreviousColors().get(player2.getPreviousColors().size() - 1) == Color.WHITE) {
                if (player1.getPreviousColors().get(player1.getPreviousColors().size() - 2) == Color.BLACK &&
                        player2.getPreviousColors().get(player2.getPreviousColors().size() - 2) == Color.WHITE) {
                    match = roundMatching.addMatch(player1, player2, tournament);
                    allMatches.add(match);
                    player1.setPreviousColors(Color.WHITE, 1);
                    player2.setPreviousColors(Color.BLACK, 1);
                } else if (player2.getPreviousColors().get(player2.getPreviousColors().size() - 2) == Color.BLACK &&
                        player1.getPreviousColors().get(player1.getPreviousColors().size() - 2) == Color.WHITE) {
                    match = roundMatching.addMatch(player2, player1, tournament);
                    allMatches.add(match);
                    player1.setPreviousColors(Color.BLACK, 1);
                    player2.setPreviousColors(Color.WHITE, 1);
                } else if (player1.getPreviousColors().get(player1.getPreviousColors().size() - 2) == Color.WHITE &&
                        player2.getPreviousColors().get(player2.getPreviousColors().size() - 2) == Color.WHITE) {
                    //System.out.println("Players 2 white color in a row:" + player1.getName() + " " + player2.getName());
                    return false;
                } else {
                    match = roundMatching.addMatch(player1, player2, tournament);
                    allMatches.add(match);
                    player1.setPreviousColors(Color.WHITE, 1);
                    player2.setPreviousColors(Color.BLACK, 1);
                }
            }
        } else {
            if(pairNumber % 2 == 0){
                match = roundMatching.addMatch(player2, player1, tournament);
                allMatches.add(match);
                player1.setPreviousColors(Color.BLACK, 1);
                player2.setPreviousColors(Color.WHITE, 1);
            } else {
                match = roundMatching.addMatch(player1, player2, tournament);
                allMatches.add(match);
                player1.setPreviousColors(Color.WHITE, 1);
                player2.setPreviousColors(Color.BLACK, 1);
            }
        }

        player1.addGame();
        player2.addGame();
        return true;
    }
}
