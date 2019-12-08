import org.newdawn.slick.*;

public class GameState extends ApplicationState{
    public GameState(SimpleSlickGame game) {
        super(game);
    }


    @Override
    void update(GameContainer gc, int delta) throws SlickException {
        game.timer -= delta;
        handleGame(gc);
        updatePositionsAndSpeedAndStates();
        if(isDead()) {
            game.changeState(new MenuState(game));
        }
        if(game.map.isFinished) {
            game.menuText = "You won";
            game.changeState(new MenuState(game));
        }
    }

    private boolean isDead() {
        if(game.player.posy > (game.mapSizeY+5)*GameConstants.BLOCK_SIZE*GameConstants.CHUNK_SIZE || game.map.isHit) {
            game.menuText = "You are dead";
            return true;
        }
        if(game.timer<0) {
            game.menuText = "You ran out of time";
            return true;
        }
        return false;
    }

    private void updateXPosAndSpeed() {
        GameObject block;
        if(game.player.speedx>0) {
            block = game.map.isCollidingWithLeft(game.player);
            if(block!=null) {
                game.player.posx = Math.min(game.player.posx+game.player.speedx, block.posx - game.player.sizex);
                game.player.speedx = 0f;
            }
        }
        else if (game.player.speedx<0) {
            block = game.map.isCollidingWithRight(game.player);
            if(block!=null) {
                game.player.posx = Math.max(game.player.posx+game.player.speedx, block.posx + block.sizex);
                game.player.speedx = 0f;
            }
        }
        game.player.posx += game.player.speedx;
    }

    private void updateYPosAndSpeed() {
        GameObject block = null;

        if(game.player.speedy>0) {
            block = game.map.isCollidingWithTop(game.player);
            if(block!=null) {
                game.player.posy = Math.min(game.player.posy+game.player.speedy, block.posy - game.player.sizey);
                game.player.speedy = 0f;
            }
        }
        else if (game.player.speedy<0) {
            block = game.map.isCollidingWithBottom(game.player);
            if(block!=null) {
                game.player.posy = Math.max(game.player.posy+game.player.speedy, block.posy + block.sizey);
                game.player.speedy = -game.player.speedy;
            }
        }

        if(block==null) {
            game.player.posy += game.player.speedy;
        }
    }

    private void updatePlayerStates() {
        game.player.isWalking = (game.player.speedx != 0f);
        game.player.isFlying = (game.player.speedy != 0f);
    }

    private void updatePositionsAndSpeedAndStates() {
        updateXPosAndSpeed();
        updateYPosAndSpeed();
        updatePlayerStates();
        game.player.updateState();
    }

    private void handleGame(GameContainer gc) throws SlickException {
        additionalKeysHandler(gc);
        addXMovementHandler(gc);
        addYMovementHandler(gc);
        game.player.updateState();
    }

    private void drawUI(Graphics g) {
        g.drawString("Time " + game.timer / 1000, 100, 100);
        g.drawString("Coins " + game.map.collectedCoins, 200, 100);
    }

    public void drawPos(Graphics g) {
        g.drawString(Float.toString(game.player.speedx), 100, 100);
        g.drawString(Float.toString(game.player.speedy), 160, 100);
        g.drawString(Float.toString(game.player.posx), 100, 150);
        g.drawString(Float.toString(game.player.posy), 160, 150);
    }

    private void addXMovementHandler(GameContainer gc) {
        if(gc.getInput().isKeyDown(GameConstants.MOVE_RIGHT_INPUT)) {
            game.player.isHeadingRight = true;
            game.player.speedx+=1f;
            game.player.speedx=Math.min(game.player.speedx, game.maxSpeed);
        } else if(gc.getInput().isKeyDown(GameConstants.MOVE_LEFT_INPUT)) {
            game.player.isHeadingRight = false;
            game.player.speedx-=1f;
            game.player.speedx=Math.max(game.player.speedx, -game.maxSpeed);
        } else {
            game.player.speedx-=Math.signum(game.player.speedx)*0.8;
            if(Math.abs(game.player.speedx)<1f) {
                game.player.speedx = 0f;
            }
        }
    }

    private void addYMovementHandler(GameContainer gc) {
        if(gc.getInput().isKeyPressed(GameConstants.MOVE_UP_INPUT) && game.player.speedy==0f) {
                game.player.speedy=-28f;
        } else if(gc.getInput().isKeyPressed(GameConstants.MOVE_DOWN_INPUT) && game.player.speedy==0f) {
            game.player.posy+=1f;
        } else{
            game.player.speedy+=1f;
            game.player.speedy=Math.min(game.player.speedy, game.maxSpeed);
        }
    }

    private void additionalKeysHandler(GameContainer gc){
        if(gc.getInput().isKeyPressed(GameConstants.RESTART_GAME_INPUT)) {
            game.player.posx = GameConstants.BLOCK_SIZE*5/2;
            game.player.posy = game.startPosY;
        }
        if(gc.getInput().isKeyPressed(Input.KEY_ESCAPE)) {
            game.menuText = "Escaped";
            game.changeState(new MenuState(game));
        }
    }

    @Override
    void drawState(GameContainer gc, Graphics g) throws SlickException {
        float camerax = 960f - game.player.sizex/2;
        float cameray = 540f - game.player.sizey/2;
        g.scale(gc.getScreenWidth()/1920f,gc.getScreenHeight()/1080f); //<< this final
        //g.scale(1920/1920f,1080/1080f);

        drawBackground(camerax, cameray, g);
        drawMap(camerax, cameray);
        drawPlayer(camerax, cameray);
        drawUI(g);
    }

    private void drawBackground(float camerax, float cameray, Graphics g) throws SlickException {
        g.setBackground(new Color(145,40,153));

        Image back2 = new Image("src\\bak2.png");
        Image back3 = new Image("src\\bak3.png");
        Image backg = new Image("src\\backg.png");

        for(int i=0; i<10; i++) {
            backg.draw((-game.player.posx+camerax)/40+3840*i-1000, (-game.player.posy+cameray)/40-100);
            back2.draw((-game.player.posx+camerax)/20+3840*i-1000, (-game.player.posy+cameray)/32-100);
            back3.draw((-game.player.posx+camerax)/10+3840*i-1000, (-game.player.posy+cameray)/24-100);
        }
    }

    private void drawMap(float camerax, float cameray) {
        for (GameObject block : game.map.getFragments()) {
            if(!block.isCollected) {
                block.draw(block.posx-game.player.posx+camerax, block.posy-game.player.posy+cameray);
            }
        }
    }

    private void drawPlayer(float camerax, float cameray) {
        game.player.animation.draw(camerax, cameray);
    }
}
