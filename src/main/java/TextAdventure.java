import java.util.Random;
import java.util.Scanner;

public class TextAdventure {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random rng = new Random();
        String playAgain;

        do {
            int coins = 20;
            int faintCount = 0;
            int healCount = 0;
            int caughtCount = 0;
            boolean playing = true;

            int pokeballs = 3;
            int potions = 1;

            System.out.println("Welcome to the world of Pokémon!");
            System.out.print("Are you ready to pick your starter Pokémon? (yes or no): ");
            String yesOrNo = sc.nextLine().toLowerCase();
            if (!yesOrNo.equals("yes")) {
                System.out.println("Well, adventure calls anyway!");
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

            System.out.println("\nYou chose " + playerPoke + "!");
            System.out.println("Poké Balls: " + pokeballs + " | Potions: " + potions + " | Coins: " + coins);

            while (playing) {
                System.out.println("\nWhere do you want to go?");
                System.out.println("1) Route 1 (Battle)");
                System.out.println("2) Pokémon Center");
                System.out.println("3) Poké Mart");
                System.out.println("4) Backpack");
                System.out.println("5) Quit Game");
                System.out.print("> ");

                int choice = 0;
                if (sc.hasNextInt()) {
                    choice = sc.nextInt();
                    sc.nextLine();
                } else {
                    sc.nextLine();
                    continue;
                }

                switch (choice) {
                    case 1: {
                        String[] wildPokemon = {"Pidgey", "Rattata", "Caterpie", "Weedle"};
                        String pokemon = wildPokemon[rng.nextInt(wildPokemon.length)];
                        int wildHP = 6;
                        int wildAttack = 0;

                        System.out.println("\nA wild " + pokemon + " appeared!");
                        boolean battling = true;

                        while (battling) {
                            System.out.println("\nYour HP: " + playerHP + "/" + maxHP + " | " + pokemon + " HP: " + wildHP);
                            System.out.print("Choose (attack/run/item): ");
                            String battleChoice = sc.nextLine().toLowerCase();

                            if (battleChoice.equals("attack")) {
                                int dmg = playerAttack + rng.nextInt(5);
                                int wildDmg = wildAttack + rng.nextInt(3);

                                System.out.println("You dealt " + dmg + " damage!");
                                wildHP -= dmg;

                                if (wildHP <= 0) {
                                    System.out.println("You defeated " + pokemon + "! You earned 10 coins!");
                                    coins += 10;
                                    battling = false;
                                    break;
                                }

                                System.out.println(pokemon + " hit you for " + wildDmg + " damage!");
                                playerHP -= wildDmg;

                                if (playerHP <= 0) {
                                    System.out.println("You fainted! Back to the Pokémon Center...");
                                    faintCount++;
                                    playerHP = maxHP;
                                    battling = false;
                                    break;
                                }

                            } else if (battleChoice.equals("run")) {
                                if (rng.nextDouble() < 0.5) {
                                    System.out.println("You ran away safely!");
                                    battling = false;
                                } else {
                                    System.out.println("Couldn't escape!");
                                }

                            } else if (battleChoice.equals("item")) {
                                System.out.println("\nItems: Poké Balls(" + pokeballs + "), Potions(" + potions + ")");
                                System.out.print("Use Poké Ball or Potion? ");
                                String itemChoice = sc.nextLine().toLowerCase();

                                if (itemChoice.contains("poké") && pokeballs > 0) {
                                    pokeballs--;
                                    System.out.println("You throw a Poké Ball...");
                                    if (rng.nextDouble() < 0.4) {
                                        System.out.println("Gotcha! " + pokemon + " was caught!");
                                        caughtCount++;
                                        battling = false;
                                    } else {
                                        System.out.println(pokemon + " broke free!");
                                    }
                                } else if (itemChoice.contains("potion") && potions > 0) {
                                    potions--;
                                    playerHP = Math.min(playerHP + 10, maxHP);
                                    System.out.println("You used a potion! HP restored to " + playerHP + "/" + maxHP);
                                } else {
                                    System.out.println("You don’t have that item!");
                                }
                            }
                        }
                    }
                    case 2: {
                        System.out.println("\nNurse Joy heals your Pokémon to full health!");
                        playerHP = maxHP;
                        healCount++;
                    }
                    case 3: {
                        boolean shopping = true;
                        while (shopping) {
                            System.out.println("\nWelcome to the Poké Mart!");
                            System.out.println("Coins: " + coins);
                            System.out.println("1) Poké Ball - 10 coins");
                            System.out.println("2) Potion - 15 coins");
                            System.out.println("3) Exit");
                            System.out.print("> ");

                            int buy = sc.nextInt();
                            sc.nextLine();

                            switch (buy) {
                                case 1:{
                                    if (coins >= 10) {
                                        pokeballs++;
                                        coins -= 10;
                                        System.out.println("Bought a Poké Ball! You now have " + pokeballs);
                                    } else {
                                        System.out.println("Not enough coins!");
                                    }
                                }
                                case 2: {
                                    if (coins >= 15) {
                                        potions++;
                                        coins -= 15;
                                        System.out.println("Bought a Potion! You now have " + potions);
                                    } else {
                                        System.out.println("Not enough coins!");
                                    }
                                }
                                case 3: shopping = false;
                                default: System.out.println("Invalid choice.");
                            }
                        }
                    }
                    case 4: {
                        System.out.println("\n🎒 Backpack:");
                        System.out.println("Poké Balls: " + pokeballs);
                        System.out.println("Potions: " + potions);
                        System.out.println("Coins: " + coins);
                        System.out.println("Caught Pokémon: " + caughtCount);
                        System.out.println("Heals used: " + healCount + " | Faints: " + faintCount);
                    }
                    case 5: {

                        if (coins >= 100 && pokeballs > 0) {
                            System.out.println("\n...Suddenly, a powerful roar echoes across the land...");
                            System.out.println("A");
                            System.out.println("A");
                            for (int i = 3; i > 0; i--) {
                                System.out.println("Shaking... " + i + "...");
                            }
                            if (rng.nextDouble() < 0.7) {
                                System.out.println("");
                                System.out.println("✨ SECRET ENDING: You became the Legendary Trainer of Kanto! ✨");
                            } else {
                                System.out.println("Mewtwo broke free and disappeared into the shadows...");
                                System.out.println("😱 SECRET ENDING FAILED: The legend continues...");
                            }
                        } else if (coins >= 80) {
                            System.out.println("\n🏆 You became a Pokémon Champion! (Good Ending)");
                        } else if (faintCount >= 3) {
                            System.out.println("\n💀 You lost too many battles... (Bad Ending)");
                        } else {
                            System.out.println("\n🚶 You return home to rest. (Neutral Ending)");
                        }

                        playing = false;
                    }
                    default: System.out.println("Invalid option!");
                }
            }

            System.out.print("\nPlay again? (yes or no): ");
            playAgain = sc.nextLine().toLowerCase();

        } while (playAgain.equals("yes"));

        System.out.println("\nThanks for playing!");
        sc.close();
    }
}


