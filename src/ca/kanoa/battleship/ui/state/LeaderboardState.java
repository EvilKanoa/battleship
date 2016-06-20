package ca.kanoa.battleship.ui.state;

import ca.kanoa.battleship.Config;
import ca.kanoa.battleship.files.Leaderboard;
import ca.kanoa.battleship.files.LeaderboardEntry;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class LeaderboardState extends BasicGameState {

    private LeaderboardEntry[] leaderboard;
    private boolean won = false;

    @Override
    public int getID() {
        return Config.SCREEN_LEADERBOARD;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {}

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics g) throws SlickException {
        String result;
        if (won) {
            result = "You won the game! :)";
        } else {
            result = "You lost the game. :(";
        }
        g.drawString(result, (Config.WINDOW_WIDTH / 2) - (g.getFont().getWidth(result) / 2), 20);

        g.drawString("Top Scores:", (Config.WINDOW_WIDTH / 2) - (g.getFont().getWidth("Top Scores:") / 2), 120);

        for (int i = 0; i < leaderboard.length; i++) {
            String str = leaderboard[i].getName() + ": " + leaderboard[i].getScore();
            g.drawString(str, (Config.WINDOW_WIDTH / 2) - (g.getFont().getWidth(str) / 2), 150 + 30 * i);
        }
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {}

    public void setLeaderboard(LeaderboardEntry[] leaderboard) {
        this.leaderboard = leaderboard;
    }

    public void setWon(boolean won) {
        this.won = won;
    }

}
