package base;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

@SuppressWarnings({"SqlNoDataSourceInspection", "Duplicates", "ConstantConditions", "EmptyCatchBlock"})
public class Menu {

	private static String previousMenu = "0 - Return to the previous menu";

	private static String url = "jdbc:postgresql://comp421.cs.mcgill.ca:5432/cs421";
    private static String usernamestring = "cs421g16";
    private static String passwordstring = "D4q3rkn1";
    private static boolean notDone = true;
    private static String usertHandle;

    private static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		//STEP 1. Import required packages
		// Register the driver.  You must register the driver before you can use it.
		try {
			DriverManager.registerDriver ( new org.postgresql.Driver() ) ;
		} catch (Exception cnfe){
			System.out.println("Class not found");
		}
		loginWithUsertHandle();

		while(notDone) {
		    System.out.println("---------- Twitbook ----------");
			System.out.println("Hello " + usertHandle + ", here are five options that you can choose from to try our super cool app!!!");
			System.out.println("1 - Friends");
			System.out.println("2 - Walls");
			System.out.println("3 - Events");
			System.out.println("4 - Notification");
			System.out.println("0 - Quit the application");
			System.out.println("Just type the number of the option you want:");
			int i = sc.nextInt();
			switch(i) {
				case 0:
					System.out.println("We are sad to see you go :(");
					notDone = false;
					break;
				case 1:
					manageFriendOption();
					break;
				case 2:
					wallMethod();
					break;
				case 3:
				    eventSubmenu();
					break;
				case 4:
					System.out.println("1 - Unread Notifications");
					System.out.println("2 - Read Notifications");
					System.out.println("3 - Mark Unread Notifications as Read");
					int j = sc.nextInt();
					switch(j) {
						case 1:
							show_Unread_Notification(usertHandle);
					        break;
                        case 2:
                            show_Read_Notification(usertHandle);
                            break;
                        case 3:
                            markAsRead(usertHandle);
                            break;
                    }
			}
		}
	}

    private static void wallMethod() {
		System.out.println("Do you want to:");
		System.out.println("1 - See the posts on your wall");
		System.out.println("2 - See the posts on a friend's wall");
		System.out.println(previousMenu);
		sc = new Scanner(System.in);
		int in = sc.nextInt();
		if(in == 1) {
			Connection con = null;
			Statement statement = null;
			ResultSet rs = null;
			try {
				con = DriverManager.getConnection (url,usernamestring, passwordstring);
				statement = con.createStatement ( ) ;
				rs = statement.executeQuery(""
						+ "SELECT text_content, like_count, handle FROM submission JOIN post ON submission_id = post_id"
						+ " WHERE submission_id IN (SELECT post_id FROM post WHERE wall_id = " +
						"(SELECT wall_id FROM creator WHERE handle = '" + usertHandle + "'));");
				int subCount = 0;
				while(rs.next()) {
					subCount++;
					System.out.println("Post " + subCount);
					System.out.println("Author: " + rs.getString(3));
					System.out.println("Likes: "+ rs.getString(2));
					System.out.println("Text: " + rs.getString(1));
				}
				System.out.println("We found " + subCount + " posts on your wall.");
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try { rs.close(); } catch (Exception e) { /* ignored */ }
				try { statement.close(); } catch (Exception e) { /* ignored */ }
				try { con.close(); } catch (Exception e) { /* ignored */ }
			}
		} else if(in == 2) {
			System.out.println("Which friend's wall do you want to see?");
			sc = new Scanner(System.in);
			String friendHandle = sc.nextLine();
			Connection con = null;
			Statement statement = null;
			ResultSet rs1 = null;
			ResultSet rs = null;
			try {
				con = DriverManager.getConnection (url,usernamestring, passwordstring);
				statement = con.createStatement ( ) ;
				rs1 = statement.executeQuery("SELECT display_name FROM usert WHERE usert_handle = '" + friendHandle + "';");
				if(rs1.next()) {
					String friendDisplayName = rs1.getString(1);
					System.out.println("Here is " + friendDisplayName + "'s wall");
					rs = statement.executeQuery(""
							+ "SELECT text_content, like_count, handle FROM submission JOIN post ON submission_id = post_id"
							+ " WHERE submission_id IN (SELECT post_id FROM post WHERE wall_id = " +
							"(SELECT wall_id FROM creator WHERE handle = '" + friendHandle + "'));");
					int subCount = 0;
					while(rs.next()) {
						subCount++;
						System.out.println("Post " + subCount);
						System.out.println("Author: " + rs.getString(3));
						System.out.println("Likes: "+ rs.getString(2));
						System.out.println("Text: " + rs.getString(1));
					}
					System.out.println("We found " + subCount + " posts on " + friendDisplayName + "'s wall.");
				} else {
					System.out.println("Sorry, no such handle exists.");
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try { rs1.close(); } catch (Exception e) { /* ignored */ }
				try { rs.close(); } catch (Exception e) { /* ignored */ }
				try { statement.close(); } catch (Exception e) { /* ignored */ }
				try { con.close(); } catch (Exception e) { /* ignored */ }
			}
		} else if(in == 0) {
			//back to the main menu
		} else {
			System.out.println("Sorry, that was not part of the options.");
		}
	}

    private static void loginWithUsertHandle() {
		boolean loggedIn = false;
		System.out.println("Welcome to Twitbook");
		while(!loggedIn) {
			System.out.println("Please enter your user handle:");
			String input = sc.nextLine();
			Connection con = null;
			Statement statement = null;
			ResultSet rs = null;
			try {
				con = DriverManager.getConnection (url,usernamestring, passwordstring);
				statement = con.createStatement ( ) ;
				rs = statement.executeQuery("SELECT * FROM usert WHERE usert_handle LIKE '" + input + "';");
				int colNum = rs.getMetaData().getColumnCount();
				int userCount = 0;
				while(rs.next()) {
					userCount++;
					for(int i = 1; i <= colNum; i++) {
						if(i == 1) {
							usertHandle = rs.getString(i);
						}
					}
					loggedIn = true;
				}
				if(!loggedIn) {
					System.out.println("Sorry, that user handle doesn't exist");
				}
				if(userCount > 1) {
					System.out.println("You should be careful, there seem to be more than one of you .......");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try { rs.close(); } catch (Exception e) { /* ignored */ }
				try { statement.close(); } catch (Exception e) { /* ignored */ }
				try { con.close(); } catch (Exception e) { /* ignored */ }
			}
		}
	}

    private static void show_Unread_Notification(String searchHandle) {

        Connection con = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            con = DriverManager.getConnection (url, usernamestring, passwordstring);
            try {

                statement = con.createStatement ( ) ;
                rs = statement.executeQuery("  SELECT notif_text, notif_time "
                        + " FROM notification "
                        + "	WHERE (read_status = FALSE OR read_status IS NULL) AND usert_handle ='" + searchHandle + "' ;" );
                ResultSetMetaData rsmd = rs.getMetaData();
                int columnsNumber = rsmd.getColumnCount();

                while (rs.next()) {
                    for (int i = 1; i <= columnsNumber; i++) {
                        if (i > 1) System.out.print(",  ");
                        String columnValue = rs.getString(i);
                        System.out.print(rsmd.getColumnName(i) + ": " + columnValue);
                    }
                    System.out.println("");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { statement.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }
    }

    private static void show_Read_Notification(String searchHandle) {

        Connection con = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            con = DriverManager.getConnection (url, usernamestring, passwordstring);
            try {

                statement = con.createStatement ( ) ;
                rs = statement.executeQuery("  SELECT notif_text, notif_time "
                        + " FROM notification "
                        + "	WHERE read_status = TRUE AND usert_handle ='" + searchHandle + "' ;" );
                ResultSetMetaData rsmd = rs.getMetaData();
                int columnsNumber = rsmd.getColumnCount();

                while (rs.next()) {
                    for (int i = 1; i <= columnsNumber; i++) {
                        if (i > 1) System.out.print(",  ");
                        String columnValue = rs.getString(i);
                        System.out.print(rsmd.getColumnName(i) + ": " + columnValue);
                    }
                    System.out.println("");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { statement.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }
    }

    private static void markAsRead(String searchHandle) {
        Connection con = null;
        Statement statement = null;
        try {
            con = DriverManager.getConnection (url, usernamestring, passwordstring);
            try {

                statement = con.createStatement ( ) ;
                int numberOfUpdatedStatements = statement.executeUpdate(" UPDATE notification"
                        + " SET read_status = TRUE"
                        + "	WHERE (read_status = FALSE OR read_status IS NULL) AND usert_handle ='" + searchHandle + "' ;" );

                System.out.println(numberOfUpdatedStatements+ " notifications set as read");

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { statement.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }
    }

    // Manage three options related to friends
    private static void manageFriendOption() {
        System.out.println("Please choose from the following options:");
        System.out.println("1 - View all your friends");
        System.out.println("2 - Add friend from search for non friends by name");
        System.out.println("3 - Add friend from list of non friends with whom you have mutual friends");
        System.out.println(previousMenu);
        int option = sc.nextInt();
        switch(option) {
            case 0:
                break;
            case 1:
                System.out.println("Your friends:");
                viewUserFriends();
                break;
            case 2:
                System.out.println("Please enter a search string to match against display names");
                sc.nextLine();
                String searchString = sc.nextLine();
                addNonFriendFromSearchByName(searchString);
                break;
            case 3:
                System.out.println("Here are users that have mutual friends with you");
                addNonFriendFromNonFriendsWithMutualFriends();
                break;
        }

    }

    // Show the user a list of all their friends
    private static void viewUserFriends() {
        Connection con = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            con = DriverManager.getConnection (url,usernamestring, passwordstring);
            try {
                statement = con.createStatement ( ) ;

                String query = "SELECT usert_handle, display_name, email FROM usert WHERE usert_handle IN " +
                        "(SELECT friend_handle FROM usert_friend WHERE usert_handle = '" + usertHandle + "');";

                rs = statement.executeQuery(query);
                ResultSetMetaData rsmd = rs.getMetaData();
                int columnsNumber = rsmd.getColumnCount();

                while (rs.next()) {
                    for (int i = 1; i <= columnsNumber; i++) {
                        if (i > 1) System.out.print(",  ");
                        String columnValue = rs.getString(i);
                        System.out.print(rsmd.getColumnName(i) + ": " + columnValue);
                    }
                    System.out.println("");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { statement.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }
    }

    // Show the user a list of users whose display name contains the searchString provided
    // Then allow them to select from this list a user to add as a friend using function manageAddingFriend
    private static void addNonFriendFromSearchByName(String searchString) {
        Connection con = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            con = DriverManager.getConnection (url,usernamestring, passwordstring);
            try {
                statement = con.createStatement();

                String query = "WITH friends AS (SELECT friend_handle FROM usert_friend WHERE usert_handle = '" + usertHandle + "') " +
                        "SELECT usert_handle, display_name FROM usert " +
                        "WHERE LOWER(display_name) LIKE LOWER('%" + searchString + "%') " +
                        "AND usert_handle != '" + usertHandle + "' AND usert_handle NOT IN (SELECT * FROM friends);";

                rs = statement.executeQuery(query);
                ResultSetMetaData rsmd = rs.getMetaData();
                int columnsNumber = rsmd.getColumnCount();
                ArrayList<String> potentialFriends = new ArrayList<>();
                while (rs.next()) {
                    potentialFriends.add(rs.getString("usert_handle"));
                    System.out.print(rs.getRow() + " - ");
                    for (int i = 1; i <= columnsNumber; i++) {
                        if (i > 1) System.out.print(",  ");
                        String columnValue = rs.getString(i);
                        System.out.print(rsmd.getColumnName(i) + ": " + columnValue);
                    }
                    System.out.println("");
                }
                manageAddingFriend(potentialFriends);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { statement.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }
    }

    // Show the user a list of users who they are not friends with but with whom they have friends in common
    // Then allow them to select from this list a user to add as a friend using function manageAddingFriend
    private static void addNonFriendFromNonFriendsWithMutualFriends() {
        Connection con = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            con = DriverManager.getConnection (url,usernamestring, passwordstring);
            try {
                statement = con.createStatement();

                String query = "WITH friends AS (SELECT friend_handle FROM usert_friend WHERE usert_handle = '" + usertHandle + "'), " +
                        "nonfriends AS (SELECT usert_handle FROM usert WHERE usert_handle != '" + usertHandle + "' AND usert_handle NOT IN (SELECT * FROM friends)) " +
                        "SELECT usert_handle, COUNT(*) num_mutual_friends FROM usert_friend " +
                        "WHERE usert_handle IN (SELECT * FROM nonfriends)" +
                        "AND friend_handle IN (SELECT * FROM friends)" +
                        "GROUP BY usert_handle ORDER BY num_mutual_friends DESC;";

                rs = statement.executeQuery(query);
                ResultSetMetaData rsmd = rs.getMetaData();
                int columnsNumber = rsmd.getColumnCount();
                ArrayList<String> potentialFriends = new ArrayList<String>();
                while (rs.next()) {
                    potentialFriends.add(rs.getString("usert_handle"));
                    System.out.print(rs.getRow() + " - ");
                    for (int i = 1; i <= columnsNumber; i++) {
                        if (i > 1) System.out.print(",  ");
                        String columnValue = rs.getString(i);
                        System.out.print(rsmd.getColumnName(i) + ": " + columnValue);
                    }
                    System.out.println("");
                }
                manageAddingFriend(potentialFriends);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { rs.close(); } catch (Exception e) { /* ignored */ }
            try { statement.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }
    }

    // Allows logged in user to chose a usert_handle from potentialFriends to be added as friend
    private static void manageAddingFriend(ArrayList<String> potentialFriends) {
        if (potentialFriends.size() == 0) {
            System.out.println("No users were found to add.");
        }
        else {
            System.out.println("Please select the number of the user you would like to add as a friend from the list above or type 0 to return to the previous menu:");
            int selection = sc.nextInt();
            while (selection < 0 || selection > potentialFriends.size()) {
                System.out.println("Please select a number from the list of users above or type 0 to return to the previous menu.");
                selection = sc.nextInt();
            }
            if (selection > 0) {
                // ArrayList potentialFriends is indexed starting at 0 even though the list of usert_handles it contains were shown as
                // options to the user as a list starting at 1 so the index of the selection they made is 1 less than the int they provided
                String friendToAdd = potentialFriends.get(selection-1);
                System.out.println("Adding friend " + friendToAdd);
                addFriend(friendToAdd);
            }
        }
    }

    // Adds friend with usert_handle newFriend to the logged in user
    private static void addFriend(String newFriend) {
        Connection con = null;
        Statement statement = null;
        try {
            con = DriverManager.getConnection (url,usernamestring, passwordstring);
            try {
                statement = con.createStatement();

                String insert = "INSERT INTO usert_friend (usert_handle, friend_handle) VALUES ('" + usertHandle + "', '" + newFriend + "');";
                int result = statement.executeUpdate(insert);

                if (result == 1) {
                    System.out.println("New friend added succesfully. Here is your updated list of friends:");
                    viewUserFriends();
                }
                else {
                    System.out.println("Something went wrong. New friends was not added succesfully.");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { statement.close(); } catch (Exception e) { /* ignored */ }
            try { con.close(); } catch (Exception e) { /* ignored */ }
        }
    }

    private static void eventSubmenu() {
		while(true) {
			System.out.println("Event Submenu");
			System.out.println("1 - Search for an Event");
			System.out.println("2 - Create an Event");
			System.out.println("3 - Subscribe to an Event");
			System.out.println("4 - Delete an Event");
			System.out.println(previousMenu);

			LOOP_I: while(true) {
				int i = sc.nextInt(); sc.nextLine();
				switch(i) {
					case 0:
						return;
					case 1:
						searchEvent();
						break LOOP_I;
					case 2:
						createEvent();
						break LOOP_I;
					case 3:
						subscribeEvent();
						break LOOP_I;
					case 4:
						deleteEvent();
						break LOOP_I;
					default:
						System.out.println("Please select a valid option.");
						break;
				}
			}
		}
	}

    private static void searchEvent() {
		Connection con = null;
		Statement statement = null;
		ResultSet rs = null;
		try {
			con = DriverManager.getConnection (url,usernamestring, passwordstring);

			while(true) {
				System.out.println("Please enter a search term...");
				String input = sc.nextLine();

				statement = con.createStatement();
				rs = statement.executeQuery("SELECT eventt_id,title,location,start_time,end_time FROM eventt WHERE title ILIKE '%" + input + "%';");
				System.out.println(String.format("%-10s %-60s %-30s %-14s %s", "EVENT ID", "TITLE", "LOCATION", "START TIME", "END TIME"));
				while (rs.next()) {
					Integer eventt_id = rs.getInt("eventt_id");
					String title = rs.getString("title");
					String location = rs.getString("location");
					Date start_time = rs.getDate("start_time");
					Date end_time = rs.getDate("end_time");

					System.out.println(String.format("%-10s %-60s %-30s %-14s %s", eventt_id, title, location, start_time, end_time));
				}

				LOOP_I: while(true) {
					System.out.println("\nEnter 1 to make another search. Enter 0 to return to the previous menu.");
					int i = sc.nextInt(); sc.nextLine();
					switch(i) {
						case 0:
							return;
						case 1:
							break LOOP_I;
						default:
							break;
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try { rs.close(); } catch(Exception e) {} // Ignore
			try { statement.close(); } catch(Exception e) {} // Ignore
			try { con.close(); } catch(Exception e) {} // Ignore
		}
	}

    private static void createEvent() {
		Connection con = null;
		Statement statement = null;
		ResultSet rs = null;
		try {
			con = DriverManager.getConnection (url,usernamestring, passwordstring);

			while(true) {
				System.out.println("Create an event...");
				System.out.println("Please enter a title for the event");
				String title = sc.nextLine();

				System.out.println("Please enter a description for the event");
				String description = sc.nextLine();

				System.out.println("Please enter a location for the event");
				String location = sc.nextLine();

				// '2018-10-29 2:05:10'
				System.out.println("Please enter a start time for the event (YYYY-MM-DD HH:MM:SS)");
				String start_time = sc.nextLine();

				System.out.println("Please enter a end time for the event (YYYY-MM-DD HH:MM:SS)");
				String end_time = sc.nextLine();

				// Create wall for event
				statement = con.createStatement();
				rs = statement.executeQuery("INSERT INTO wall(description) VALUES('"+title+"') RETURNING wall_id;");

				Integer wall_id = null;
				if (rs.next()) {
					wall_id = rs.getInt("wall_id");
				} else {
					System.out.println("Unknown Error. Returning to previous menu...");
					return;
				}

				// Create Event
				Integer return_value = statement.executeUpdate("INSERT INTO eventt(handle, wall_id, start_time, end_time, title, description, location) " +
						"VALUES('"+usertHandle+"',"+wall_id+",'"+start_time+"','"+end_time+"','"+title+"','"+description+"','"+location+"')");
				if (return_value > 0) {
					System.out.println("Event Created!");
					LOOP_I: while(true) {
						System.out.println("\nEnter 1 to create another event. Enter 0 to go back to the previous menu");
						int i = sc.nextInt(); sc.nextLine();
						switch(i) {
							case 0:
								return;
							case 1:
								break LOOP_I;
							default:
								break;
						}
					}
				} else {
					System.out.println("Unknown Failure. Please contact a system administrator");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try { rs.close(); } catch(Exception e) {} // Ignore
			try { statement.close(); } catch(Exception e) {} // Ignore
			try { con.close(); } catch(Exception e) {} // Ignore
		}
	}

    private static void subscribeEvent() {
		Connection con = null;
		Statement statement = null;
		try {
			con = DriverManager.getConnection (url,usernamestring, passwordstring);

			while(true) {
				System.out.println("Subscribe to an event...");
				System.out.println("Please enter the 'EVENT ID' of the event you wish to subscribe to");
				Integer event_id = sc.nextInt(); sc.nextLine();

				String rsvp_status = null;
				LOOP_I: while(true) {
					System.out.println("Please enter your RSVP status...");
					System.out.println("1 - Going");
					System.out.println("2 - Not Going");
					System.out.println("3 - Maybe");

					int i = sc.nextInt(); sc.nextLine();
					switch(i) {
						case 1:
							rsvp_status = "going";
							break LOOP_I;
						case 2:
							rsvp_status = "not going";
							break LOOP_I;
						case 3:
							rsvp_status = "maybe";
							break LOOP_I;
						default:
							break;
					}
				}

				// Subscribe to the event
				statement = con.createStatement();
				Integer return_value = statement.executeUpdate("INSERT INTO eventt_subscription (usert_handle, eventt_id, rsvp_status) "
						+ "VALUES('"+usertHandle+"','"+event_id+"','"+rsvp_status+"');");

				if (return_value > 0) {
					System.out.println("Subscribed!");
					LOOP_I: while(true) {
						System.out.println("\nEnter 1 to subscribe to another event. Enter 0 to go back to the previous menu");
						int i = sc.nextInt(); sc.nextLine();
						switch(i) {
							case 0:
								return;
							case 1:
								break LOOP_I;
							default:
								break;
						}
					}
				} else {
					System.out.println("Unknown Failure. Please contact a system administrator");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try { statement.close(); } catch(Exception e) {} // Ignore
			try { con.close(); } catch(Exception e) {} // Ignore
		}
	}

    private static void deleteEvent() {
		Connection con = null;
		Statement statement = null;
		ResultSet rs = null;
		try {
			con = DriverManager.getConnection (url,usernamestring, passwordstring);

			while(true) {
				System.out.println("Delete an event (subscribers will be notified)...");
				System.out.println("Please enter the 'EVENT ID' of the event you wish to DESTROY");
				Integer event_id = sc.nextInt(); sc.nextLine();

				// Notify subscribers
				statement = con.createStatement();
				statement.executeUpdate("INSERT INTO notification(usert_handle, notif_text) "
						+ "SELECT usert_handle, 'The event with ID "+event_id+" has been cancelled' "
						+ "FROM eventt_subscription WHERE eventt_id = "+event_id+" AND rsvp_status IS NOT NULL AND rsvp_status <> 'not going';");

				// Delete subscriptions
				statement.executeUpdate("DELETE FROM eventt_subscription WHERE eventt_id = "+event_id+";");

				// Get wall_id
				rs = statement.executeQuery("SELECT wall_id FROM eventt WHERE eventt_id = "+event_id+";"); rs.next();
				Integer wall_id = rs.getInt("wall_id");

				// Delete Event
				statement.executeUpdate("DELETE FROM eventt WHERE eventt_id = "+event_id+";");

				// Delete Wall
				statement.executeUpdate("DELETE FROM wall WHERE wall_id = "+wall_id+";");

				System.out.println("DESTRUCTION COMPLETE!");
				LOOP_I: while(true) {
					System.out.println("\nEnter 1 to DESTROY another event. Enter 0 to go back to the previous menu");
					int i = sc.nextInt(); sc.nextLine();
					switch(i) {
						case 0:
							return;
						case 1:
							break LOOP_I;
						default:
							break;
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try { rs.close(); } catch(Exception e) {} // Ignore
			try { statement.close(); } catch(Exception e) {} // Ignore
			try { con.close(); } catch(Exception e) {} // Ignore
		}
	}
}
