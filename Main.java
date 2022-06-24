import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main{
    private static Map<Block,Map<Block,Long>> map;
    private static Block start = null;
    private static Block end = null;
    private static Long maxLength = null;

    private static List<Block> getBlocksFromFile(String filename){
        List<Block> list = new LinkedList<Block>();
        try{
            Scanner reader = new Scanner(new File(filename));
            while(reader.hasNextLine()){
                String[] splittedLine = reader.nextLine().split(";");
                list.add(new Block(splittedLine[0],new Coord(Integer.parseInt(splittedLine[1]),Integer.parseInt(splittedLine[2]))));
            }
            reader.close();
        }catch(FileNotFoundException ex){
            ex.printStackTrace();
            return null;
        }
        return list;
    }

    private static void buildGraph(List<Block> blocks){
        for (Block block : blocks) {
            map.put(block,new HashMap<Block,Long>());
            for (Block otherBlock : blocks) {
                if(block == otherBlock){
                    map.get(block).put(otherBlock,(long)0);
                }else if((block.getCoord().getX() - 1 == otherBlock.getCoord().getX() && block.getCoord().getY() == otherBlock.getCoord().getY() && block.getColor().equals(otherBlock.getColor())) ||
                         (block.getCoord().getX() + 1 == otherBlock.getCoord().getX() && block.getCoord().getY() == otherBlock.getCoord().getY() && block.getColor().equals(otherBlock.getColor())) ||
                         (block.getCoord().getY() - 1 == otherBlock.getCoord().getY() && block.getCoord().getX() == otherBlock.getCoord().getX() && block.getColor().equals(otherBlock.getColor())) ||
                         (block.getCoord().getY() + 1 == otherBlock.getCoord().getY() && block.getCoord().getX() == otherBlock.getCoord().getX() && block.getColor().equals(otherBlock.getColor()))){
                            map.get(block).put(otherBlock,(long)1);
                }else{
                    map.get(block).put(otherBlock,(long)Integer.MAX_VALUE);
                }
            }
        }
    }

    private static void floydWarshall(List<Block> blocks){
        buildGraph(blocks);

        for (Block k : blocks) {
            for (Block i : blocks) {
                for (Block j : blocks) {
                    if(map.get(i).get(j) > map.get(i).get(k) + map.get(k).get(j)){
                        map.get(i).remove(j);
                        map.get(i).put(j,(long)(map.get(i).get(k) + map.get(k).get(j)));
                        if(i.equals(j) && map.get(i).get(i) < 0){
                            //neg cycle, but here it is not possible
                        }
                    }
                }
            }
        }
    }

    private static void getLongestPathWithSameColor(){
        maxLength = (long)0;
        map.forEach((keyFirst,valueFirst) ->{
            valueFirst.forEach((keySecond,valueSecond) -> {
                if(valueSecond != Integer.MAX_VALUE && valueSecond > maxLength){
                    maxLength = valueSecond;
                    start = keyFirst;
                    end = keySecond;
                }
            });
        });
    }

    private static void printMap(){
        map.forEach((firstKey,firstValue) ->{
            System.out.println(firstKey.toString() + " -> ");
            firstValue.forEach((secondKey,secondValue) -> {
                System.out.println("\t" + secondKey.toString() + "\t" + secondValue);
            });
        });
    }

    public static void main(String args[]){
        map = new HashMap<>();
        floydWarshall(getBlocksFromFile("test.txt"));
        getLongestPathWithSameColor();
        //printMap();
        System.out.println("-------------------The Floyd-Warshall algorithm, time complexity: O(n^3)-------------------");
        System.out.println("The color of the longest path: " + start.getColor());
        System.out.println("The length of the longest path: " + maxLength + 1); //plus 1 is required because we count the steps we have to take
        System.out.println("Start: " + start.getCoord().toString() + " , End: " + end.getCoord().toString());
    }
}