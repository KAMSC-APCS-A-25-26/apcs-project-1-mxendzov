import java.util.Random;
import java.util.Scanner;

public class TextAdventure {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random rng = new Random();


        System.out.println("Welcome to the world of Pokémon!");
        System.out.println("Today is the day you start your journey as a Pokémon Trainer.\n");
        System.out.print("Are you ready to pick your starter pokemon? (Yes or No) ");
        String yesOrNo = sc.nextLine().toLowerCase();
        if (yesOrNo.equals("yes")){
            System.out.println("\nThen go right ahead");
        } else {
            System.out.println("\nWell I don't care. ");
        }


        System.out.println("\nChoose your starter Pokémon:\n");
        String[] starters = {"Bulbasaur", "Charmander", "Squirtle"};
        for (int i = 0; i < starters.length; i++) {
            System.out.println((i + 1) + ") " + starters[i]);
        }

        int starterChoice = 0;
        while (starterChoice < 1 || starterChoice > starters.length) {
            System.out.print("\nEnter 1, 2, or 3: ");
            if (sc.hasNextInt()) {
                starterChoice = sc.nextInt();
                sc.nextLine();
            } else {
                sc.nextLine();
            }
        }

        String playerPoke = starters[starterChoice - 1];
        int playerHP = 15;
        int maxHP = 15;
        int playerAttack = 0;


        System.out.println("\nYou chose " + playerPoke + " as your starter!");

        boolean playing = true;
        while(playing){
            System.out.println("Where do you want to go?");
            System.out.println("1) Route 1");
            System.out.println("2) Pokémon Center");
            System.out.println("3) Quit Game");
            System.out.print("> ");

            int pChoice = 0;
            pChoice = sc.nextInt();
            sc.nextLine();
            switch (pChoice) {
                case 1:
            String[] wildPokemon = {"Pidgey", "Rattata", "Caterpie", "Weedle"};
            String pokemon = wildPokemon[rng.nextInt(wildPokemon.length)];
            int wildHP = 6;
            int wildAttack = 0;
            System.out.println("A wild " + pokemon + " appeared!");

            boolean battle = true;
            while (battle) {
                System.out.println("\nYour HP: " + playerHP + "/" + maxHP +
                        " | Wild " + pokemon + " HP: " + wildHP);
                System.out.print("Choose: attack or run > ");
                String choice = sc.nextLine().trim().toLowerCase();

                if (choice.equals("attack")) {
                    int dmg = playerAttack + rng.nextInt(4);
                    int wildDmg = wildAttack + rng.nextInt(3);

                    System.out.println("You deal " + dmg + " damage!");
                    wildHP -= dmg;

                    if (wildHP <= 0) {
                        System.out.println("You defeated " + pokemon + "! Victory!");
                        battle = false;
                        break;
                    }

                    System.out.println(pokemon + " dealt " + wildDmg + " damage!");
                    playerHP -= wildDmg;

                    if (playerHP <= 0) {
                        System.out.println("You fainted! Game over.");
                        battle = false;
                        break;
                    }

                } else if (choice.equals("run")) {
                    if (rng.nextDouble() < 0.5) {
                        System.out.println("You ran away safely!");
                        battle = false;
                    } else {
                        System.out.println("Couldn't escape!");
                    }
                } else {
                    System.out.println("Invalid choice. Type 'attack' or 'run'.");
                }

            } break;
                case 2:
                    System.out.println("\nYou arrive at the Pokémon Center. Nurse Joy greets you warmly.");
                    System.out.println("\"Let's heal your Pokémon!\"");
                    playerHP = maxHP;
                    System.out.println("Your " + playerPoke + " is fully healed!");
                    break;
                case 3:
                    System.out.println("\nYou decide to end your adventure for today. See you next time!");
                    playing = false;
                    break;

                default:
                    System.out.println("\nInvalid input. Please choose 1, 2, or 3.");
        }
        }
    }
}


