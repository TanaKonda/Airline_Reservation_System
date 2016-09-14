import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;


public class DataManager {
	
	public static void exportData(String filename,
			 ArrayList<Passenger> passengers, ArrayList<Flight> flights)
			 {
			try{
				File file = new File(filename);
				PrintStream ps = new PrintStream(file);
				ps.println("#flightCount "+flights.size());
				for(int i=0;i<flights.size();i++)
				{
					ps.println("#newFlight");
					ps.println(flights.get(i).getSourceAirport()+" , "+flights.get(i).getDestinationAirport()+" , "+ flights.get(i).getTakeoffTime()+" , "+flights.get(i).getLandingTime());
					ps.println(flights.get(i).getCapacity());
				}
				ps.println("#passCount "+passengers.size());
				for(int i=0;i<passengers.size();i++)
				{
					ps.println("#newPass");
					ps.println(passengers.get(i).getFirstName()+" , "+passengers.get(i).getLastName());
					ps.println(passengers.get(i).getAlerts().size());
					
					for(int j=0; j<passengers.get(i).getAlerts().size();j++ )
					{ps.println(passengers.get(i).getAlerts().get(j));}
					
					ps.println(passengers.get(i).getBookedFlights().size());
					for(int k=0; k<passengers.get(i).getBookedFlights().size();k++)
					{ps.println(passengers.get(i).getBookedFlights().get(k).getSourceAirport()+" , "+passengers.get(i).getBookedFlights().get(k).getDestinationAirport()+" , "+passengers.get(i).getBookedFlights().get(k).getTakeoffTime()+" , "+passengers.get(i).getBookedFlights().get(k).getLandingTime());}
					
					ps.println(passengers.get(i).getStandbyFlights().size());
					for(int k=0; k<passengers.get(i).getStandbyFlights().size();k++)
					{ps.println(passengers.get(i).getStandbyFlights().get(k).getSourceAirport()+" , "+passengers.get(i).getStandbyFlights().get(k).getDestinationAirport()+" , "+passengers.get(i).getStandbyFlights().get(k).getTakeoffTime()+" , "+passengers.get(i).getStandbyFlights().get(k).getLandingTime());}
				}
				ps.close();
			
				} catch(FileNotFoundException e){
					System.out.println("exportData error.");
					return;
				}
		
			 }
			
	public static ImportData importData(String filename)
			{
				try{
					Scanner input = new Scanner(new File(filename));
					ArrayList<Flight> flightlist = new ArrayList<Flight>();
					input.next(); // Grabs #flightCount. Discard.
					int fcount = input.nextInt(); // grabs the number of flights. Store for loop.
					for(int i=0;i<fcount;i++)
					{
						input.next(); // read and discard #newFlight
						String src = input.next(); // takeoff air
						input.next();//comma
						String dest = input.next();// dest air
						input.next();//comma
						int takeoff = input.nextInt();// takeoff time
						input.next();//comma
						int landing = input.nextInt();//dest time
						int capacity = input.nextInt();// capacity
						
						flightlist.add(new Flight(src,dest,takeoff,landing,capacity));
					}
					ArrayList<Passenger> passlist = new ArrayList<>();
					input.next(); // Grabs #passCount. Discard.
					int pcount = input.nextInt(); // grabs the number of passengers. Store for loop.
					for(int i=0;i<pcount;i++)
					{
						input.next(); // read and discard #newPass
						String first = input.next(); // Tana
						input.next(); // comma
						String last = input.next(); // Konda
						
						Passenger pass = new Passenger(first,last);
						passlist.add(pass);
						int alertcount = input.nextInt(); // No. of alerts. 

						for(int j = 0; j <alertcount;j++)
						{
							String alert = input.next() + input.nextLine();
							pass.addAlert(alert);
						}
						
						int bookedflightcount = input.nextInt(); // No. of booked flights.
						for(int j = 0; j <bookedflightcount;j++)
						{
							String src = input.next(); // takeoff air
							input.next();//comma
							String dest = input.next();// dest air
							input.next();//comma
							int takeoff = input.nextInt();// takeoff time
							input.next();//comma
							int landing = input.nextInt();//dest time
							for(int k=0;k<flightlist.size();k++)
							{
								if(flightlist.get(k).getSourceAirport().equals(src) && flightlist.get(k).getDestinationAirport().equals(dest) && flightlist.get(k).getTakeoffTime()==takeoff && flightlist.get(k).getLandingTime()==landing)
									pass.getBookedFlights().add(flightlist.get(k));
							}
						}
						//same with standbyflight.
						int standbyflightcount = input.nextInt(); // No. of standby flights.
						for(int j = 0; j <standbyflightcount;j++)
						{
							String src = input.next(); // takeoff air
							input.next();//comma
							String dest = input.next();// dest air
							input.next();//comma
							int takeoff = input.nextInt();// takeoff time
							input.next();//comma
							int landing = input.nextInt();//dest time
							for(int k=0;k<flightlist.size();k++)
							{
								if(flightlist.get(k).getSourceAirport().equals(src) && flightlist.get(k).getDestinationAirport().equals(dest) && flightlist.get(k).getTakeoffTime()==takeoff && flightlist.get(k).getLandingTime()==landing)
									pass.addStandbyFlight(flightlist.get(k));
							}
						}
						
					}
					
					input.close();
					return new ImportData(passlist,flightlist);
					

				} catch(FileNotFoundException e){
					
			 return new ImportData(new ArrayList<Passenger>(), new ArrayList<Flight>());
				}
			}

}
