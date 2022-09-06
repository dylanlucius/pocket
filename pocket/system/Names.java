package pocket.system;

import pocket.creature.*;

public class Names {
    
    public static String returnProposal(Entity entity){
        String firstName , lastName, proposal;
        String[] manNames = {"Aaron", "Alex", "Andrew", "Bernard", "Buddy", "Benjamin", "Charles", "Carl", "Carlos", "Christopher", "Cedric", "Carter", "Dylan", "Daniel", "Donald", "Dino", "David", "Eric", "Ethan", "Franklin", "Fred", "Gus", "Gustav", "Gino", "Gary", "Harold", "Harry", "Heathe", "Ivan", "Isaac", "Isaiah", "John", "Jack", "Jeff", "Jerry", "Ken", "Keith", "Lance", "Mike", "Michael", "Matthew", "Ned", "Neil", "Peter", "Prince", "Paul", "Phil", "Ronald", "Regis", "Ryan", "Steve", "Scott", "Stanley", "Tyler", "Toby", "Ulysses", "Vince"},
                 womanNames = {"Marge", "Sarah"},
                 lastNames = {"Smith", "Johnson", "Black"},
                 petNames = {"Fluffy", "Sparky", "Sprocket", "Charlie", "Beethoven", "Mozart"};

        
        if(entity.name != null){
           switch(entity.name){
                default:
                    System.out.println("Name was not null");
                    proposal = "?";
                    break;

                case "Man":
            
                    firstName = manNames[ Dice.random.nextInt( manNames.length ) ];
                    lastName = lastNames[ Dice.random.nextInt( lastNames.length ) ];
                    proposal  = firstName + " " + lastName;
                    break;

                case "Woman":
        
                    firstName = womanNames[ Dice.random.nextInt( womanNames.length ) ];
                    lastName = lastNames[ Dice.random.nextInt( lastNames.length ) ];
                    proposal  = firstName + " " + lastName;
                    break;

                case "Cat":
                    proposal = petNames[ Dice.random.nextInt( petNames.length ) ];
                    break;

                case "Dog":
                proposal = petNames[ Dice.random.nextInt( petNames.length ) ];
                break;
            }
        
                

        }
        else {
            proposal = "?";
        }

        return proposal;
    }
}
