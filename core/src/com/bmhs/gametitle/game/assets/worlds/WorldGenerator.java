package com.bmhs.gametitle.game.assets.worlds;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.MathUtils;
import com.bmhs.gametitle.gfx.assets.tiles.statictiles.WorldTile;
import com.bmhs.gametitle.gfx.utils.TileHandler;

import java.util.logging.FileHandler;


public class WorldGenerator {

    private int worldMapRows, worldMapColumns;

    private int currentColor;
    private int[][] worldIntMap;

    private int seedColor, seedColor2, Red, Black, darkGray, Gray;

    public WorldGenerator(int worldMapRows, int worldMapColumns) {
        this.worldMapRows = worldMapRows;
        this.worldMapColumns = worldMapColumns;

        worldIntMap = new int[worldMapRows][worldMapColumns];

        seedColor = 2;
        Red = 14;
        Black = 12;
        darkGray = 10;
        Gray = 8;
        seedColor2 = 10;


        //call methods to build 2D array


        seedIslands(5);
        searchAndExpand(MathUtils.random(4, 5), seedColor, Black, 0.96);
        searchAndExpand(MathUtils.random(5, 6), Black, Red, 0.5);
        searchAndExpand(MathUtils.random(4, 5), Red, darkGray, 0.10);

        seedIslands2(17);
        searchAndExpand2(MathUtils.random(3, 4), darkGray, Gray, 0.4);
        searchAndExpand2(8, Gray, 6, 0.2);


        generateWorldTextTile();

        Gdx.app.error("WorldGenerator", "WorldGenerator(WorldTile[][][])");
    }


    private void seedIslands(int num) {
        for (int i = 0; i < num; i++) {
            int rSeed = MathUtils.random(worldIntMap.length - 1);
            int cSeed = MathUtils.random(worldIntMap[0].length - 1);
            worldIntMap[rSeed][cSeed] = seedColor;
        }
    }

    private void seedIslands2(int numb) {
        for (int i = 0; i < numb; i++) {
            int rSeed = MathUtils.random(worldIntMap.length - 1);
            int cSeed = MathUtils.random(worldIntMap[0].length - 1);
            worldIntMap[rSeed][cSeed] = seedColor2;
        }
    }



    private void searchAndExpand(int radius, int numToFind, int numToWrite, double probability) {
        for (int r = 0; r < worldIntMap.length; r++) {
            for (int c = 0; c < worldIntMap[r].length; c++) {

                if (worldIntMap[r][c] == numToFind) {
                    for (int subRow = r - radius; subRow <= r + radius; subRow++) {
                        for (int subCol = c - radius; subCol <= c + radius; subCol++) {
                            if (subRow >= 0 && subCol >= 0 && subRow <= worldIntMap.length - 1 && subCol <= worldIntMap[0].length - 1 && worldIntMap[subRow][subCol] != numToFind) {
                                if (Math.random() > probability) {
                                    worldIntMap[subRow][subCol] = numToWrite;

                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void searchAndExpand2(int radius, int numToFind, int numToWrite, double probability) {
        for (int r = 0; r < worldIntMap.length; r++) {
            for (int c = 0; c < worldIntMap[r].length; c++) {

                if (worldIntMap[r][c] == numToFind) {
                    for (int subRow = r - radius; subRow <= r + radius; subRow++) {
                        for (int subCol = c - radius; subCol <= c + radius; subCol++) {
                            if (subRow >= 0 && subCol >= 0 && subRow <= worldIntMap.length - 1 && subCol <= worldIntMap[0].length - 1 && worldIntMap[subRow][subCol] != numToFind) {
                                if (Math.random() > probability) {
                                    worldIntMap[subRow][subCol] = numToWrite;

                                }
                            }
                        }
                    }
                }
            }
        }
    }




    public String getWorld3DArrayToString() {
        String returnString = "";

        for(int r = 0; r < worldIntMap.length; r++) {
            for(int c = 0; c < worldIntMap[r].length; c++) {
                returnString += worldIntMap[r][c] + " ";
            }
            returnString += "\n";
        }

        return returnString;
    }

    public void randomize() {
        for(int r = 0; r < worldIntMap.length; r++) {
            for(int c = 0; c < worldIntMap[r].length; c++) {
                worldIntMap[r][c] = MathUtils.random(TileHandler.getTileHandler().getWorldTileArray().size-1);
            }
        }
    }


    public WorldTile[][] generateWorld() {
        WorldTile[][] worldTileMap = new WorldTile[worldMapRows][worldMapColumns];
        for(int r = 0; r < worldIntMap.length; r++) {
            for(int c = 0; c < worldIntMap[r].length; c++) {
                worldTileMap[r][c] = TileHandler.getTileHandler().getWorldTileArray().get(worldIntMap[r][c]);
            }
        }
        return worldTileMap;
    }

    private void generateWorldTextTile() {
        FileHandle file = Gdx.files.local("assets/worlds/world.txt");
        file.writeString(getWorld3DArrayToString(), false);
    }

}
