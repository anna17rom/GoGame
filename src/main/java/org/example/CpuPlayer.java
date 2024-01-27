package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Set;
import static java.lang.Math.pow;

public class CpuPlayer extends Player {

    private final static ObjectMapper MAPPER = new ObjectMapper();


    @Override
    public ServerResponse.Mode mode() {
        return ServerResponse.Mode.WITH_BOT;
    }

    @Override
    public GoBoard.Stone nextMove(GoBoard board){
        /*Set<Intersection> used = new HashSet<>();*/
        GoBoard.Stone stone=null;
        boolean NOempty = false;
        /*while (!NOempty){*/
        int size = board.getNumberOfSquares();
        int randomX = (int) (Math.random() * size);
        int randomY = (int) (Math.random() * size);
        /*used.add(board.getIntersectionFromArray(randomX,randomY));
        if (used.size()>=pow(board.getNumberOfSquares()+1,2)){
            this.EnforsedPass();
            break;
        }
        if (board.getIntersectionFromArray(randomX,randomY).isEmpty()){
            NOempty=true;
            moveCount++;*/
        stone = new GoBoard.Stone((randomX+1)* board.getGridSize(),(randomY+1)* board.getGridSize(), GoBoard.StoneColor.WHITE);
        /* }else {NOempty=false;}}*/
        return stone;
    }

    @Override
    public void sendBoard(GoBoard board) {
        ServerResponse response = new ServerResponse();
        response.setType(ServerResponse.Type.MOVE_REQUEST);
        response.setBoard(board);
    }

    @Override
    public void sendGameStarted(int player, GoBoard board) {
        ServerResponse response = new ServerResponse();
        response.setPlayerNo(player);
        board.setNumberOfSquares(size);
        response.setBoard(board);
        response.setType(ServerResponse.Type.GAME_STARTED);
    }

    public int getCapturedStones() {
        return capturedStones;
    }

    @Override
    public int getIntersectionsInTerritories() {
        return IntersectionsInTerritories;
    }

    public void addCapturedStones(int nb) {
        capturedStones += nb;
    }

    @Override
    public Set<Set<Intersection>> getTerritories() {
        return territories;
    }

    @Override
    public Set<Set<Intersection>> setTerritories(Set<Set<Intersection>> territories) {
        return this.territories=territories;
    }



    @Override
    public void addTerritory(Set<Intersection> territory) {
        territories.add(territory);
    }


    public void removeCapturedStones(int nb) { capturedStones -= nb; }

    @Override
    public boolean passed() {
        return this.IfPassed;
    }

    @Override
    public void EnforsedPass() {
        this.IfPassed=true;
    }


    @Override
    public void sendGameOver() {
        ServerResponse response = new ServerResponse();
        response.setType(ServerResponse.Type.GAME_ENDED);
    }

    public void calculateTerritory() {
        for (Set<Intersection> territory : territories) {
            IntersectionsInTerritories += territory.size();
        }
    }
    public void setSize(int size){this.size=size;}
}
