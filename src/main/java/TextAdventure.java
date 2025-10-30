import java.util.Random;
import java.util.Scanner;

public class TextAdventure {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Random rng = new Random();


        System.out.println("Choose your starter Pokémon:\n\n");
        String[] starters = {"Bulbasaur (Grass)", "Charmander (Fire)", "Squirtle (Water)"};
        for (int i = 0; i < starters.length; i++) {
            System.out.println((i + 1) + ") " + starters[i]);
        }
        int starterChoice = 0;
        while (starterChoice < 1 || starterChoice > starters.length) {
            System.out.print("\nEnter 1, 2, or 3: ");
            if (in.hasNextInt()) {
                starterChoice = in.nextInt();
                in.nextLine();
            } else {
                in.nextLine();
            }
        }

        String playerPoke = starters[starterChoice - 1].split(" ")[0];
        int playerHP = 15;
        int maxHP = 15;
        int playerAttack = 0;
        System.out.println("\nYou chose " + playerPoke + " as your starter!");

        String wildPoke = "Pidgey";
        int wildHP = 6;
        int wildAttack = 0;
        System.out.println("A wild " + wildPoke + " appeared!");
        boolean battle = true;

        while (battle) {
            System.out.println("\nYour HP: " + playerHP + " | Wild " + wildPoke + " HP: " + wildHP);
            System.out.print("Choose: attack or run | ");
            String choice = in.nextLine().trim().toLowerCase();

            if (choice.equals("attack")) {
                int dmg = playerAttack + rng.nextInt(4);
                if (dmg == 0) {
                    System.out.println("You missed! ");
                }
                wildHP -= dmg;

                int wildDmg = wildAttack + rng.nextInt(3);
                if (wildDmg == 0){
                    System.out.println( wildPoke + " missed! at");
                }

                System.out.println("You deal " + dmg + " damage!");

                playerHP -= wildDmg;
                System.out.println(wildPoke + " dealt " + wildDmg + " damage!");

                if (wildHP <= 0) {
                    System.out.println("You defeated " + wildPoke + "! Victory!");
                    battle = false;
                    break;
                }


            }
        }
    }}

