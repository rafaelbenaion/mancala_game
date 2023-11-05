import java.util.List;
import java.util.ArrayList;

public class MinMax {

    private static final int MAX_DEPTH = 4;

    public String findBestMove(Engine engine, int player) {
        String move = max(engine, player, 0, Integer.MIN_VALUE, Integer.MAX_VALUE).move;

        if (move == null) {

            List<String>    moves       = getLegalMoves(engine, player);
            int             randomIndex = (int) (Math.random() * moves.size());
                            move        = moves.get(randomIndex);
        }

        return move;
    }

    private MoveScore max(Engine engine, int player, int depth, int alpha, int beta) {

        if (depth == MAX_DEPTH || !engine.getGameStatus()) {
            return new MoveScore(evaluate(engine, player), null);
        }

        String  bestMove = null;
        int     maxScore = Integer.MIN_VALUE;

        for (String move : getLegalMoves(engine, player)) {

            Engine clonedEngine = engine.cloneEngine();

            if (clonedEngine.move(move, player)) {

                int score = min(clonedEngine, 1 - player, depth + 1, alpha, beta).score;

                if (score > maxScore) {
                    maxScore = score;
                    bestMove = move;
                }

                alpha = Math.max(alpha, score);

                if (beta <= alpha) {
                    break;
                }
            }
        }

        return new MoveScore(maxScore, bestMove);
    }

    private MoveScore min(Engine engine, int player, int depth, int alpha, int beta) {

        if (depth == MAX_DEPTH || !engine.getGameStatus()) {
            return new MoveScore(evaluate(engine, player), null);
        }

        String  bestMove = null;
        int     minScore = Integer.MAX_VALUE;

        for (String move : getLegalMoves(engine, player)) {

            Engine clonedEngine = engine.cloneEngine();

            if (clonedEngine.move(move, player)) {

                int score = max(clonedEngine, 1 - player, depth + 1, alpha, beta).score;

                if (score < minScore) {
                    minScore = score;
                    bestMove = move;
                }

                beta = Math.min(beta, score);

                if (beta <= alpha) {
                    break;
                }
            }
        }

        return new MoveScore(minScore, bestMove);
    }

    private int evaluate(Engine engine, int player) {

        if (player == 0) {
            return engine.getPlayer1().getSeeds() - engine.getPlayer2().getSeeds();
        } else {
            return engine.getPlayer2().getSeeds() - engine.getPlayer1().getSeeds();
        }
    }

    public List<String> getLegalMoves(Engine engine, int player) {

        List<String> legalMoves = new ArrayList<>();

        for (int caseNumber = 1; caseNumber <= 16; caseNumber++) {

            if (engine.isValidMove(player, caseNumber, "R")) {
                legalMoves.add(caseNumber + "R");
            }
            if (engine.isValidMove(player, caseNumber, "B")) {
                legalMoves.add(caseNumber + "B");
            }
            if (engine.isValidMove(player, caseNumber, "TR")) {
                legalMoves.add(caseNumber + "TR");
            }
            if (engine.isValidMove(player, caseNumber, "TB")) {
                legalMoves.add(caseNumber + "TB");
            }

        }
        return legalMoves;
    }

    private static class MoveScore {
        int     score;
        String  move;

        MoveScore(int score, String move) {
            this.score  = score;
            this.move   = move;
        }
    }
}