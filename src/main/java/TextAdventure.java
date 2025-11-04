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

            // Leveling system setup
            int level = 1;
            int xp = 0;
            int xpToNext = level * 10;

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
                System.out.println("5) Gym Battle");
                System.out.println("6) Are you a Pokemon Master (ends the game)");
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
                        int wildHP = 6 + rng.nextInt(4);
                        int wildAttack = rng.nextInt(3);

                        System.out.println("\nA wild " + pokemon + " appeared!");
                        boolean battling = true;

                        while (battling) {
                            System.out.println("\nYour HP: " + playerHP + "/" + maxHP +
                                    " | " + pokemon + " HP: " + wildHP +
                                    " | LVL: " + level + " (" + xp + "/" + xpToNext + " XP)");
                            System.out.print("Choose (attack/run/item): ");
                            String battleChoice = sc.nextLine().toLowerCase();

                            if (battleChoice.equals("attack")) {
                                int routeDmg = playerAttack + rng.nextInt(5);
                                int wildDmg = wildAttack + rng.nextInt(3);

                                System.out.println("You dealt " + routeDmg + " damage!");
                                wildHP -= routeDmg;

                                if (wildHP <= 0) {
                                    System.out.println("You defeated " + pokemon + "! You earned 5 coins and 10 XP!");
                                    coins += 5;
                                    xp += 10;

                                    // Level-up check
                                    if (xp >= xpToNext) {
                                        level++;
                                        xp = xp - xpToNext;
                                        xpToNext = level * 10;
                                        maxHP += 2;
                                        playerAttack += 1;
                                        System.out.println("\n🎉 " + playerPoke + " leveled up to Level " + level + "!");
                                        System.out.println("Stats increased! HP: " + maxHP + " | Attack: " + playerAttack);
                                    }

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
                        break;
                    }

                    case 2: {
                        System.out.println("\nNurse Joy heals your Pokémon to full health!");
                        playerHP = maxHP;
                        healCount++;
                        break;
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
                                case 1:
                                    if (coins >= 10) {
                                        pokeballs++;
                                        coins -= 10;
                                        System.out.println("Bought a Poké Ball! You now have " + pokeballs);
                                    } else {
                                        System.out.println("Not enough coins!");
                                    }
                                    break;
                                case 2:
                                    if (coins >= 15) {
                                        potions++;
                                        coins -= 15;
                                        System.out.println("Bought a Potion! You now have " + potions);
                                    } else {
                                        System.out.println("Not enough coins!");
                                    }
                                    break;
                                case 3:
                                    shopping = false;
                                    break;
                                default:
                                    System.out.println("Invalid choice.");
                            }
                        }
                        break;
                    }

                    case 4: {
                        System.out.println("\n🎒 Backpack:");
                        System.out.println("Poké Balls: " + pokeballs);
                        System.out.println("Potions: " + potions);
                        System.out.println("Coins: " + coins);
                        System.out.println("Caught Pokémon: " + caughtCount);
                        System.out.println("Heals used: " + healCount + " | Faints: " + faintCount);
                        System.out.println("Level: " + level + " | XP: " + xp + "/" + xpToNext);
                        break;
                    }

                    case 5: { // GYM BATTLE
                        if (coins < 50) {
                            System.out.println("\n🏟️ The Gym Leader blocks your way: “You’re not ready yet! Earn at least 50 coins first!”");
                            break;
                        }

                        System.out.println("\n🏟️ You enter the Pewter City Gym!");
                        System.out.println("Gym Leader Brock challenges you with his Onix!");
                        int gymHP = 20 + (level * 2);
                        int gymAttack = 1 + (level / 2);
                        boolean gymBattle = true;

                        while (gymBattle) {
                            System.out.println("\nYour HP: " + playerHP + "/" + maxHP +
                                    " | Onix HP: " + gymHP +
                                    " | LVL: " + level + " (" + xp + "/" + xpToNext + " XP)");
                            System.out.print("Choose (attack/item): ");
                            String gymChoice = sc.nextLine().toLowerCase();

                            if (gymChoice.equals("attack")) {
                                int gymDmg = playerAttack + rng.nextInt(5) + 3;
                                int onixDmg = gymAttack + rng.nextInt(4);
                                System.out.println("You dealt " + gymDmg + " damage!");
                                gymHP -= gymDmg;

                                if (gymHP <= 0) {
                                    System.out.println("\nYou defeated Onix!");
                                    System.out.println("🏅 You earned the Boulder Badge, 50 coins, and 30 XP!");
                                    coins += 50;
                                    xp += 30;

                                    if (xp >= xpToNext) {
                                        level++;
                                        xp = xp - xpToNext;
                                        xpToNext = level * 10;
                                        maxHP += 2;
                                        playerAttack += 1;
                                        System.out.println("\n🎉 " + playerPoke + " leveled up to Level " + level + "!");
                                        System.out.println("Stats increased! HP: " + maxHP + " | Attack: " + playerAttack);
                                    }

                                    // Major decision point after gym
                                    boolean decisionMade = false;
                                    boolean rushedLeague = false;
                                    while (!decisionMade) {
                                        System.out.println("\n🗺️ What will you do next?");
                                        System.out.println("1) Challenge the Pokémon League immediately!");
                                        System.out.println("2) Train further on Route 1 to level up and earn more coins.");
                                        System.out.print("> ");

                                        String decision = sc.nextLine();
                                        if (decision.equals("1")) {
                                            System.out.println("\n🚀 You rush to the Pokémon League!");
                                            coins += 20; 
                                            xp += 20;    
                                            rushedLeague = true;
                                            decisionMade = true;
                                        } else if (decision.equals("2")) {
                                            System.out.println("\n💪 You return to Route 1 to train and prepare.");
                                            decisionMade = true;
                                        } else {
                                            System.out.println("Invalid choice! Please enter 1 or 2.");
                                        }
                                    }

                                    // Rival encounter if rushed
                                    if (rushedLeague) {
                                        System.out.println("\n⚡ Your rival appears and challenges you!");
                                        int rivalHP = 15 + level * 2;
                                        int rivalAttack = 1 + level / 2;
                                        boolean rivalBattle = true;

                                        while (rivalBattle) {
                                            System.out.println("\nYour HP: " + playerHP + "/" + maxHP +
                                                    " | Rival's Pokémon HP: " + rivalHP);
                                            System.out.print("Choose (attack/item): ");
                                            String rivalChoice = sc.nextLine().toLowerCase();

                                            if (rivalChoice.equals("attack")) {
                                                int rivalDmg = playerAttack + rng.nextInt(5);
                                                int rivalHit = rivalAttack + rng.nextInt(4);
                                                System.out.println("You dealt " + rivalDmg + " damage!");
                                                rivalHP -= rivalDmg;

                                                if (rivalHP <= 0) {
                                                    System.out.println("\nYou defeated your rival!");
                                                    coins += 30;
                                                    xp += 20;
                                                    System.out.println("💰 Earned 30 coins and 20 XP!");
                                                    rivalBattle = false;
                                                    break;
                                                }

                                                System.out.println("Rival hit you for " + rivalHit + " damage!");
                                                playerHP -= rivalHit;

                                                if (playerHP <= 0) {
                                                    System.out.println("\nYou fainted against your rival!");
                                                    faintCount++;
                                                    playerHP = maxHP;
                                                    rivalBattle = false;
                                                    break;
                                                }
                                            } else if (rivalChoice.equals("item")) {
                                                if (potions > 0) {
                                                    potions--;
                                                    playerHP = Math.min(playerHP + 10, maxHP);
                                                    System.out.println("You used a potion! HP restored to " + playerHP + "/" + maxHP);
                                                } else {
                                                    System.out.println("You’re out of potions!");
                                                }
                                            } else {
                                                System.out.println("Invalid choice!");
                                            }
                                        }

                                        
                                        System.out.println("\n🏆 After defeating your rival, you are ready to become a Pokémon Master!");
                                        choice = 6; 
                                        gymBattle = false;
                                        break;
                                    }

                                    gymBattle = false;
                                    break;
                                }

                                System.out.println("Onix hit you for " + onixDmg + " damage!");
                                playerHP -= onixDmg;

                                if (playerHP <= 0) {
                                    System.out.println("\nYou fainted! Brock says, “Train harder, rookie!”");
                                    faintCount++;
                                    playerHP = maxHP;
                                    gymBattle = false;
                                    break;
                                }

                            } else if (gymChoice.equals("item")) {
                                if (potions > 0) {
                                    potions--;
                                    playerHP = Math.min(playerHP + 10, maxHP);
                                    System.out.println("You used a potion! HP restored to " + playerHP + "/" + maxHP);
                                } else {
                                    System.out.println("You’re out of potions!");
                                }
                            } else {
                                System.out.println("Invalid choice!");
                            }
                        }
                        break;
                    }

                    case 6: { // Ending case
    System.out.println("\n🌅 The sun sets over the region, painting the skies in gold and crimson...");
    
    if (level >= 6 && coins >= 100 && xp >= 80 && faintCount <= 2 && healCount <= 7) {
        System.out.println("🏆 You stand at the peak of your journey, a true Pokémon Champion!");
        System.out.println("Through battles, challenges, and relentless training, you've proven your strength and determination.");
        System.out.println("Your name will be remembered in the halls of trainers for years to come. ✨");
    } else if (level >= 5 && coins >= 80 && faintCount <= 3) {
        System.out.println("👍 You became a Pokémon Champion, though the journey was not without its struggles.");
        System.out.println("Every victory and defeat shaped you into a stronger trainer, and your story inspires others.");
    } else if (faintCount >= 3) {
        System.out.println("💀 Many battles were lost, and the path was harsh...");
        System.out.println("Yet every challenge taught you resilience. Someday, you will rise again stronger than ever.");
    } else {
        System.out.println("🚶 You return home, reflecting on your journey with pride and lessons learned.");
        System.out.println("Though the League remains unconquered, your heart and courage grew with every step.");
    }

    System.out.println("\n✨ Thank you for embarking on this adventure. Your legacy as a trainer has begun! ✨");
    playing = false;
    break;
}


                    default:
                        System.out.println("Invalid option!");
                }
            }

            System.out.print("\nPlay again? (yes or no): ");
            playAgain = sc.nextLine().toLowerCase();

        } while (playAgain.equals("yes"));

        System.out.println("\nThanks for playing!");
        sc.close();
    }
}
