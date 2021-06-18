package ro.javaweb.wordsearchapi.WordGridServices;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class WordGridService {

        private enum Direction {
            HORIZONTAL,
            VERTICAL,
            DIAGONAL,
            HORIZONTAL_INVERSE,
            VERTICAL_INVERSE,
            DIAGONAL_INVERSE
        }

        private class Coordinate{
            int x,y;

            Coordinate(int x, int y){
                this.x = x;
                this.y = y;
            }
        }

        public char[][] generateGrid(int gridSize, List<String> words){
            List<Coordinate> coordinates = new ArrayList<>();
            int x,y;
            char[][] contents = new char[gridSize][gridSize];
            for(int i = 0; i<gridSize; i++){
                for (int j = 0;j< gridSize; j++) {
                    coordinates.add(new Coordinate(i,j));
                    contents[i][j] = '_';
                }
            }

        Collections.shuffle(coordinates);
        for (String word:words) {
            Collections.shuffle(coordinates);
            for(Coordinate coordinate : coordinates){
                x=coordinate.x;
                y=coordinate.y;
                Direction selectedDirection = getDirectionForFit(contents, word,coordinate);

                if (selectedDirection != null) {
                    switch (selectedDirection){
                        case HORIZONTAL:
                            for(char c : word.toCharArray()) {
                                contents[x][y++] = c;
                            }
                            break;
                        case VERTICAL:
                            for(char c : word.toCharArray()) {
                                contents[x++][y] = c;
                            }
                            break;
                        case DIAGONAL:
                            for(char c : word.toCharArray()) {
                                contents[x++][y++] = c;
                            }
                            break;
                        case HORIZONTAL_INVERSE:
                            for(char c : word.toCharArray()) {
                                contents[x][y--] = c;
                            }
                            break;
                        case VERTICAL_INVERSE:
                            for(char c : word.toCharArray()) {
                                contents[x--][y] = c;
                            }
                            break;
                        case DIAGONAL_INVERSE:
                            for(char c : word.toCharArray()) {
                                contents[x--][y--] = c;
                            }
                            break;
                    }
                    break;
                }
            }
        }
        randomFillGrid(contents);
        return contents;
    }


        public void displayGrid(char[][] contents){
            int gridSize = contents[0].length;
        for(int i = 0; i<gridSize; i++){
            for (int j = 0; j< gridSize; j++) {
                System.out.print(contents[i][j]+ " ");
            }
            System.out.println("");
        }

    }



        private void randomFillGrid(char[][] contents){
            int gridSize = contents[0].length;
            String allCapLetters="ABCDEFGHIJKLMNOPQRSTUVWQYZ";

        for(int i = 0; i<gridSize; i++){
            for (int j = 0; j< gridSize; j++) {
                if(contents[i][j]=='_'){
                    int randomIndex = ThreadLocalRandom.current().nextInt(0, allCapLetters.length());
                    contents[i][j] = allCapLetters.charAt(randomIndex);
                }
            }
        }

    }

        private Direction getDirectionForFit(char[][] contents, String word, Coordinate coordinate){
        List<Direction> directions = Arrays.asList(Direction.values());
        Collections.shuffle(directions);
        for(Direction direction : directions){
            if(doesFit(contents, word, coordinate, direction))
                return direction;
        }

        return null;

    }

        private boolean doesFit(char[][] contents, String word, Coordinate coordinate, Direction direction){
            int gridSize = contents[0].length;
        switch (direction) {
            case HORIZONTAL:
                if (coordinate.y + word.length() < gridSize) {
                    for (int i = 0; i < word.length(); i++) {
                        if (contents[coordinate.x][coordinate.y + i] != '_')
                            return false;
                    }
                    return true;

                }
                break;

            case VERTICAL:
                if (coordinate.x + word.length() < gridSize) {
                    for (int i = 0; i < word.length(); i++) {
                        if (contents[coordinate.x + i][coordinate.y] != '_')
                            return false;
                    }
                    return true;

                }
                break;

            case DIAGONAL:
                if (coordinate.x + word.length() < gridSize && coordinate.y + word.length() < gridSize) {
                    for (int i = 0; i < word.length(); i++) {
                        if (contents[coordinate.x + i][coordinate.y + i] != '_')
                            return false;
                    }
                    return true;

                }
                break;

            case HORIZONTAL_INVERSE:
                if (coordinate.y >= word.length()-1) {
                    for (int i = 0; i < word.length(); i++) {
                        if (contents[coordinate.x][coordinate.y - i] != '_')
                            return false;
                    }
                    return true;

                }
                break;

            case VERTICAL_INVERSE:
                if (coordinate.x >= word.length()-1) {
                    for (int i = 0; i < word.length(); i++) {
                        if (contents[coordinate.x - i][coordinate.y] != '_')
                            return false;
                    }
                    return true;

                }
                break;

            case DIAGONAL_INVERSE:
                if (coordinate.x >= word.length()-1  && coordinate.y >= word.length()-1) {
                    for (int i = 0; i < word.length(); i++) {
                        if (contents[coordinate.x - i][coordinate.y - i] != '_')
                            return false;
                    }
                    return true;

                }
                break;
        }
        return false;



    }

    }