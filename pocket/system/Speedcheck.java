package pocket.system;

public class Speedcheck {
    
    
    public static boolean fullspeed(){
        return true;
    }

    public static boolean halfspeed(int step){
       
        boolean flag = false;

        switch(step){

            default:
                flag = false;
                break;

            case 0:
                flag = true;
                break;
            
            case 2:
                flag = true;
                break;                      

            case 4:
                flag = true;
                break;

            case 6:
                flag = true;
                break;

            case 8:
                flag = true;
                break;

            case 10:
                flag = true;
                break;

            case 12:
                flag = true;
                break;

            case 14:
                flag = true;
                break;
        }

        return flag;
    }

    public static boolean quarterspeed(int step){
        
        boolean flag = false;

        switch(step){

            default:
                flag = false;
                break;

            case 0:
                flag = true;
                break;                    

            case 4:
                flag = true;
                break;

            case 8:
                flag = true;
                break;

            case 12:
                flag = true;
                break;
        }

        return flag;
    }

    public static boolean eighthspeed(int step){
        
        boolean flag = false;

        switch(step){

            default:
                flag = false;
                break;

            case 0:
                flag = true;
                break;

            case 8:
                flag = true;
                break;
        }

        return flag;
    }

    public static boolean sixteenthspeed(int step){
        
        boolean flag = false;

        switch(step){

            default:
                flag = false;
                break;

            case 0:
                flag = true;
                break;
        }

        // System.out.println("\n"+flag);

        return flag;
    }

}
