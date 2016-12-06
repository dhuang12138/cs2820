package production;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * 
 * @author josephtleiferman, dihuang
 * Floor mainly works for layout purpose
 *
 */
public class Floor {
	public static Point SHELVE_1 = new Point(2,2,"SHELVE_1");
    public static Point SHELVE_2 = new Point(3,2,"SHELVE_2");
    public static Shelf[] SHELVES = {new Shelf(SHELVE_1), new Shelf(SHELVE_2)}; // initialize shelves
    public static final Point CHARGER = new Point(2,0,"CHARGER");
    public static final Point RECEIVING_DOCK = new Point(5,0,"RECEIVING_DOCK");
    public static final Point PICKER = new Point(1,5,"PICKER");
    public static final Point PICKER_WAITTING_AREA = new Point(2,5,"PICKER_WAITTING_AREA");
    public static final Point PACKER = new Point(1,2,"PACKER");
    public static Point[] PICKERBELT = { new Point(0,5,"PICKERBELT5"),new Point(0,4,"PICKERBELT4"),new Point(0,3,"PICKERBELT3") };
    public static Point[] PACKERBELT = { new Point(0,2,"PICKERBELT2"),new Point(0,1,"PICKERBELT1"),new Point(0,0,"PICKERBELT0") };
	public static final int width = 60, height = 60;
	public static final int gridSize = 10;
	public static final int UPPERB = 5;
    public static final int LOWERB = 0;
	public static LinkedList<Directions> getRoute(Point start, Point end) {
		LinkedList<Directions> route = new LinkedList<>();
        Point currentLocation = new Point(start.getX(),start.getY(),"currentLocation");
        // alternator will alternate between odd and even so that the robot will move
        // in either the x or y direction until   is in line with either the x or y
        int alternator = 0;
        // will create a route until object is at final location
        while((currentLocation.getX() != end.getX()) || (currentLocation.getY() != end.getY())) {
            
            if(alternator%2==0 && currentLocation.getX() != end.getX()) {
                // find whether moving left or right will get object closer to destination
                int diff1 = Math.abs(currentLocation.getX()+1 - end.getX());
                int diff2 = Math.abs(currentLocation.getX()-1 - end.getX());
                Point tempLocation = new Point(currentLocation.getX(),currentLocation.getY(),"tempLocation");
                if(diff1<diff2) {
                    tempLocation.setPoint(tempLocation.getX()+1, tempLocation.getY()); 
                    if(tempLocation.getX()<UPPERB) {
                        currentLocation.setPoint(currentLocation.getX()+1,currentLocation.getY());
                        route.add(Directions.RIGHT);
                    }
                }
                else {
                	tempLocation.setPoint(tempLocation.getX()-1, tempLocation.getY());
                    
                    if( tempLocation.getX()>LOWERB ) {
                    	currentLocation.setPoint(currentLocation.getX()-1,currentLocation.getY());
                        route.add(Directions.LEFT);
                    }
                }
            }else if(alternator%2==1 && currentLocation.getY() != end.getY()) {
                
            	// find whether moving up or down will get object closer to destination
                int diff1 = Math.abs(currentLocation.getY()+1 - end.getY());
                int diff2 = Math.abs(currentLocation.getY()-1 - end.getY());
                Point tempLocation = new Point(currentLocation.getX(),currentLocation.getY(),"tempLocation");
                if(diff1<diff2) {
                	tempLocation.setPoint(tempLocation.getX(), tempLocation.getY()+1);
                    
                    if( tempLocation.getY()>LOWERB) {
                    	currentLocation.setPoint(currentLocation.getX(),currentLocation.getY()+1);
                        route.add(Directions.DOWN);
                    }
                }
                else {
                	tempLocation.setPoint(tempLocation.getX(), tempLocation.getY()-1);
                    if( tempLocation.getY()<UPPERB) {
                    	currentLocation.setPoint(currentLocation.getX(),currentLocation.getY()-1);
                        route.add(Directions.UP);
                    }
                }
            }
            
            alternator +=1;
        }
        return route;
	}
	/**************************************************************
	 * following methods are not used for our simulation pattern right now
	 */
   public static HashMap<String,Point> FLOOR_LOCATIONS = new HashMap<>();
   // initially just statically create these objects
   public Floor() {
	   FLOOR_LOCATIONS.put("SHELVE_1",SHELVES[0].getPos());
       FLOOR_LOCATIONS.put("SHELVE_2",SHELVES[1].getPos());
       FLOOR_LOCATIONS.put("CHARGER",CHARGER);
       FLOOR_LOCATIONS.put("RECEIVING_DOCK",RECEIVING_DOCK);
       FLOOR_LOCATIONS.put("PICKER",PICKER);
       FLOOR_LOCATIONS.put("PACKER",PACKER);
   }
   public Point getLocation(String l) {
       return FLOOR_LOCATIONS.get(l);
   }
   public boolean objectAt(Point l) {
       for(Point x: FLOOR_LOCATIONS.values() ) {
           if(x.getX() == l.getX() && x.getY()== l.getY()) {
               return true;
           }
       }
       return false;
   }
   public void updateObjectLocation(String object, Point location) { 
       Point newLocation = FLOOR_LOCATIONS.get(object);
       newLocation = location;
       FLOOR_LOCATIONS.put(object, newLocation);
       System.out.println(object + "'s location updated too" + " (" + location.getX() + "," + location.getY()+  ")");

   }
}

/**
 * 
 * @author josephtleiferman
 * Actually I think we can just generate route by using a collection of points to store each step
 * rather than using Directions. Anyway, I implement it by your way
 *
 */
enum Directions {
    UP,DOWN,LEFT,RIGHT
}
