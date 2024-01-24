package org.example.DB;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.example.GoBoard.Stone;


import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

public class GoDb {
    private final EntityManager em;

    public GoDb(EntityManager em) {
        this.em = em;
    }

    public void saveMove(String sessionId, String playerId, Stone stone, int moveNo) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Session session = new Session();
        session.setId(sessionId);
        Player player = new Player();
        player.setId(playerId);
        Move move = new Move();
        move.setX(stone.getX());
        move.setY(stone.getY());
        move.setColor(stone.getColor().ordinal());
        move.setId(UUID.randomUUID().toString());
        move.setMoveNo(moveNo);
        player.setMoves(Arrays.asList(move));
        session.setPlayers(Collections.singletonList(player));
        em.persist(session);
        transaction.commit();
    }
}
