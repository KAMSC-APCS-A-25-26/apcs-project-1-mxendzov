import java.util.Random;
import java.util.Scanner;

public class TextAdventure {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random rng = new Random();
        String playAgain;

        do {
            // --- Player starting stats ---
            int coins = 20;
            int faintCount = 0;
            int healCount = 0;
            int caughtCount = 0;
            boolean playing = true;
            boolean forceEnd = false; // Used to trigger auto-ending after gym/rival events

            int pokeballs = 3;
            int potions = 1;

            // --- Leveling system ---
            int level = 1;
            int xp = 0;
            int xpToNext = level * 10;

            // --- Flags for endgame events ---
            boolean legendaryEncountered = false;
            boolean legendaryCaught = false;

            // --- Introduction ---
            System.out.println("Welcome to the world of Pokémon!");
            System.out.print("Are you ready to pick your starter Pokémon? (yes or no): ");
            String yesOrNo = sc.nextLine().toLowerCase();
            if (!yesOrNo.equals("yes")) {
                System.out.println("Well, adventure calls anyway!");
            }

            // --- Starter Pokémon selection ---
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

            // --- Initialize player Pokémon stats ---
            String playerPoke = starters[starterChoice - 1];
            int playerHP = 15;
            int maxHP = 15;
            int playerAttack = 0;
            boolean firstRouteBattle = true;

            System.out.println("\nYou chose " + playerPoke + "!");
            System.out.println("Poké Balls: " + pokeballs + " | Potions: " + potions + " | Coins: " + coins);

            // --- Main game loop ---
            while (playing) {
                int choice = 0;

                // --- Display main menu (if not forcing end sequence) ---
                if (!forceEnd) {
                    System.out.println("\nWhere do you want to go?");
                    System.out.println("1) Route 1 (Battle)");
                    System.out.println("2) Pokémon Center");
                    System.out.println("3) Poké Mart");
                    System.out.println("4) Backpack");
                    System.out.println("5) Gym Battle");
                    System.out.println("6) Are you a Pokémon Master (End Game)");
                    System.out.print("> ");

                    if (sc.hasNextInt()) {
                        choice = sc.nextInt();
                        sc.nextLine();
                    } else {
                        sc.nextLine();
                        continue;
                    }
                } else {
                    choice = 6; // Skip to endgame sequence automatically
                    forceEnd = false;
                }

                switch (choice) {
                    // === Route 1 (Wild Pokemon Battles) ===
                    case 1: {
                        if (firstRouteBattle) {
                            System.out.println("A wild Pokémon approaches!");
                            firstRouteBattle = false;
                        }

                        // Random wild Pokemon encounter
                        String[] wildPokemon = {"Pidgey", "Rattata", "Caterpie", "Weedle"};
                        String pokemon = wildPokemon[rng.nextInt(wildPokemon.length)];
                        int wildHP = 6 + rng.nextInt(4);
                        int wildAttack = rng.nextInt(3);

                        System.out.println("\nA wild " + pokemon + " appeared!");
                        boolean battling = true;

                        while (battling) {
                            // Display current stats during battle
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

                                // Wild Pokemon defeated
                                if (wildHP <= 0) {
                                    System.out.println("You defeated " + pokemon + "! You earned 5 coins and 10 XP!");
                                    coins += 5;
                                    xp += 10;

                                    // Level up check
                                    if (xp >= xpToNext) {
                                        level++;
                                        xp -= xpToNext;
                                        xpToNext = level * 10;
                                        maxHP += 2;
                                        playerAttack += 1;
                                        System.out.println("\n🎉 " + playerPoke + " leveled up to Level " + level + "!");
                                        System.out.println("Stats increased! HP: " + maxHP + " | Attack: " + playerAttack);
                                    }
                                    battling = false;
                                    break;
                                }

                                // Wild Pokémon attacks
                                System.out.println(pokemon + " hit you for " + wildDmg + " damage!");
                                playerHP -= wildDmg;

                                // Player faints
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
                                // Item use (Poke Ball or Potion)
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

                    // === Pokemon Center ===
                    case 2:
                        System.out.println("\nNurse Joy heals your Pokémon to full health!");
                        playerHP = maxHP;
                        healCount++;
                        break;

                    // === Poke Mart ===
                    case 3:
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

                    // === Backpack Menu ===
                    case 4:
                        System.out.println("\n🎒 Backpack:");
                        System.out.println("Poké Balls: " + pokeballs);
                        System.out.println("Potions: " + potions);
                        System.out.println("Coins: " + coins);
                        System.out.println("Caught Pokémon: " + caughtCount);
                        System.out.println("Heals used: " + healCount + " | Faints: " + faintCount);
                        System.out.println("Level: " + level + " | XP: " + xp + "/" + xpToNext);
                        break;

                    // === Gym Battle (Brock) ===
                    case 5:
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

                                    // Level up if XP reached
                                    if (xp >= xpToNext) {
                                        level++;
                                        xp -= xpToNext;
                                        xpToNext = level * 10;
                                        maxHP += 2;
                                        playerAttack += 1;
                                        System.out.println("\n🎉 " + playerPoke + " leveled up to Level " + level + "!");
                                        System.out.println("Stats increased! HP: " + maxHP + " | Attack: " + playerAttack);
                                    }

                                    // --- Choice after gym ---
                                    System.out.println("\n🏁 You defeated the Gym Leader!");
                                    System.out.println("Do you want to (1) Return to Route 1 to train or (2) Continue to the Pokémon League?");
                                    System.out.print("> ");
                                    int postGymChoice = sc.nextInt();
                                    sc.nextLine();

                                    if (postGymChoice == 1) {
                                        System.out.println("\nYou decide to train more on Route 1 before taking on the League!");
                                        break; // Ends gym loop, returns to main menu
                                    } else {
                                        System.out.println("\n🚀 You head straight to the Pokémon League!");
                                        // Trigger rival battle automatically
                                        forceEnd = true;
                                    }

                                    gymBattle = false;
                                    break;
                                }

                                // Onix attacks
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

                    // === Endgame / Pokémon Master ===
                    case 6:
                        System.out.println("\n🌅 The sun sets over the region, signaling the end of your journey...");

                        // === Legendary Mew Encounter (Secret Ending) ===
                        if (!legendaryEncountered && level >= 5 && caughtCount >= 2 && faintCount < 2 && pokeballs >= 3) {
                            legendaryEncountered = true;
                            System.out.println("\n🌟 Suddenly, a mysterious glow appears in the distance...");
                            System.out.println("A legendary Pokémon emerges! It's Mew!");

                            int legendaryHP = 25;
                            int legendaryAttack = 2;
                            boolean battlingLegendary = true;

                            while (battlingLegendary) {
                                System.out.println("\nYour HP: " + playerHP + "/" + maxHP + " | Mew HP: " + legendaryHP);
                                System.out.print("Choose (attack/run/item): ");
                                String choiceLeg = sc.nextLine().toLowerCase();

                                if (choiceLeg.equals("attack")) {
                                    int dmg = playerAttack + rng.nextInt(3);
                                    int legDmg = legendaryAttack + rng.nextInt(5);

                                    System.out.println("You dealt " + dmg + " damage!");
                                    legendaryHP -= dmg;

                                    if (legendaryHP <= 0) {
                                        System.out.println("Mew is weakened but escapes! Perhaps next time...");
                                        battlingLegendary = false;
                                        break;
                                    }

                                    System.out.println("Mew attacks for " + legDmg + " damage!");
                                    playerHP -= legDmg;
                                    if (playerHP <= 0) {
                                        System.out.println("You fainted from the intense battle!");
                                        faintCount++;
                                        playerHP = maxHP;
                                        battlingLegendary = false;
                                        break;
                                    }

                                } else if (choiceLeg.equals("item")) {
                                    System.out.println("\nItems: Poké Balls(" + pokeballs + "), Potions(" + potions + ")");
                                    System.out.print("Use Poké Ball or Potion? ");
                                    String itemChoice = sc.nextLine().toLowerCase();

                                    if (itemChoice.contains("poké") && pokeballs > 0) {
                                        pokeballs--;
                                        System.out.println("You throw a Poké Ball at Mew...");
                                        if (rng.nextDouble() < 0.3) {
                                            System.out.println("🎉 Incredible! You caught Mew!");
                                            legendaryCaught = true;
                                            battlingLegendary = false;
                                        } else {
                                            System.out.println("Mew broke free!");
                                        }
                                    } else if (itemChoice.contains("potion") && potions > 0) {
                                        potions--;
                                        playerHP = Math.min(playerHP + 10, maxHP);
                                        System.out.println("You used a potion! HP restored to " + playerHP + "/" + maxHP);
                                    } else {
                                        System.out.println("You don’t have that item!");
                                    }

                                } else if (choiceLeg.equals("run")) {
                                    System.out.println("Mew doesn’t let you escape!");
                                } else {
                                    System.out.println("Invalid choice!");
                                }
                            }
                        }

                        // === Endgame Outcomes ===
                        if (legendaryCaught) {
                            System.out.println("\n🌌 Secret Legendary Ending Unlocked! 🌌");
                            System.out.println("With Mew by your side, you are recognized as a true Pokémon Master!");
                        } else if (level >= 6 && coins >= 100 && faintCount <= 2 && healCount <= 7) {
                            System.out.println("🏆 You stand at the peak of your journey, a true Pokémon Champion!");
                        } else if (level >= 5 && coins >= 80 && faintCount <= 3) {
                            System.out.println("👍 You became a Pokémon Champion, though the journey was not without its struggles.");
                        } else if (faintCount >= 3) {
                            System.out.println("💀 Many battles were lost, and the path was harsh...");
                        } else {
                            System.out.println("🚶 You return home, reflecting on your journey with pride and lessons learned.");
                        }

                        playing = false;
                        break;

                    default:
                        System.out.println("Invalid option!");
                }
            }

            // --- Replay Option ---
            System.out.print("\nPlay again? (yes or no): ");
            playAgain = sc.nextLine().toLowerCase();

        } while (playAgain.equals("yes"));

        // --- Exit Game ---
        System.out.println("\nThanks for playing!");
        sc.close();
    }
}
