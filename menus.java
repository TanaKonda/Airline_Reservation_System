import java.util.Scanner;
public class menus {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		boolean menu1 = true;
		boolean menu2 = false;
		String first = "";
		String last = "";
		
		while(menu1)
		{
			
			System.out.println("============ Menu 1 ============");
			System.out.print("Enter option (1,2,3): ");
			
			if(input.hasNextInt())
			{
				int op = input.nextInt();
				
				if(op==1)
				{
					System.out.print("Enter your first name: ");
					 first = input.next();
					
					System.out.print("Enter your last name: ");
					 last = input.next();
					
					System.out.println("newPassenger: "+first+" "+last);
					menu2 = true;
				}
				else if(op==2)
				{
					System.out.print("Enter your first name: ");
					 first = input.next();
				
					System.out.print("Enter your last name: ");
					 last = input.next();
					
					System.out.println("findPassenger: "+first+" "+last);
					menu2 = true;
					
				}
				else if(op==3)
				{ System.exit(2);}
				else
				{
					System.out.println("Error, only 1,2,3 are acceptable inputs. Try again.");
					continue;
				}
			}
			else
			{
				System.out.println("Error, only 1,2,3 are acceptable inputs. Restart program and try again.");
				System.exit(1);
			}
			
			while(menu2)
			{
				System.out.println("============ Menu 2 ============");
				System.out.print("Enter option (1,2,3,4): ");
				
				if(input.hasNextInt())
				{
					int op = input.nextInt();
					
					if(op==1)
					{
						System.out.print("Enter <Originating airport> (three letter code): ");
						String org = input.next();
						
						System.out.print("Enter <Destination airport> (three letter code): ");
						String dest = input.next();
						
						System.out.print("findAvailableFlightPlans: "+org+" "+dest);
						
						continue;
					}
					else if(op==2)
					{
						String[] itinerary = RandomItinerary.get();
						for(int i=0; i<itinerary.length;i++)
						{
							System.out.println((i+1)+". "+itinerary[i]);
						}
						System.out.println((itinerary.length+1)+". cancel");
						int cancelno = itinerary.length+1;
						System.out.println("Enter flight number: ");
						if(input.hasNextInt())
						{
							cancelno = input.nextInt();
							if(cancelno>0 && cancelno<=itinerary.length)
							System.out.println("cancelFlight: "+itinerary[cancelno-1]+" "+first+" "+last);
											
						}
						else
						System.out.println("Error- String detected! Only integer values from 1 to "+(itinerary.length+1)+" allowed. Restart program and try again.");
						System.exit(3);
						
					}
					else if(op==3)
					{ String[] itinerary = RandomItinerary.get();
						for (int i=0; i<itinerary.length;i++)
						{
							System.out.println("cancelFlight: "+itinerary[i]+" "+first+" "+last);
						}
						
						continue;
					}
					else if(op==4)
					{ break;}
					else
					{
						System.out.println("Error, only 1,2,3,4 are acceptable inputs. Try again.");
						continue;
					}
				}
				else
				{
					System.out.println("Error, only 1,2,3,4 are acceptable inputs. Restart program and try again.");
					System.exit(1);
				}
				
			}
			
		}
	
		
		input.close();
	}

}
