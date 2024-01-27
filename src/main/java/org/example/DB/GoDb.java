package org.example.DB;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.example.GoBoard.Stone;


import java.util.*;
import java.util.stream.Collectors;

public class GoDb {
    private final EntityManager em;

    public GoDb(EntityManager em) {
        this.em = em;
    }

    public void savePlayers(String sessionId, List<String> players) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Session session = new Session();
        session.setId(sessionId);
        session.setPlayers(players.stream().map(s -> {

            Player player = new Player();
            player.setId(s);
            return player;
        }).toList());

        em.persist(session);
        transaction.commit();
    }

    public void saveMove(String sessionId, String playerId, Stone stone, int moveNo) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        Session session = em.find(Session.class, sessionId);
        List<Player> players = session.getPlayers();

        Move move = new Move();
        move.setX(stone.getX());
        move.setY(stone.getY());
        move.setColor(stone.getColor().ordinal());
        move.setId(UUID.randomUUID().toString());
        move.setMoveNo(moveNo);

        Optional<Player> player = players.stream().filter(p -> playerId.equals(p.getId())).findFirst();
        player.ifPresent(p -> {
            List<Move> moves = p.getMoves() == null ? new ArrayList<>() : new ArrayList<>(p.getMoves());
            moves.add(move);
            p.setMoves(moves);
            em.persist(player.get());
        });

        transaction.commit();
    }
    public List<String> getPlayersMoves(String id) {
        Player player = em.find(Player.class, id);
        List<Move> moves = player.getMoves();
        return moves.stream().map(move -> move.toString()).toList();
    }
}
