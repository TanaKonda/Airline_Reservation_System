import java.util.ArrayList;


public class Passenger 
{
	
	private String First;
	private String Last;
	
	private ArrayList<String> alerts;
	
	private ArrayList<Flight> BookedFlights;
	
	private ArrayList<Flight> StandbyFlights;
	
	public Passenger(String first, String last)
	{
		First = setName(first);
		Last = setName(last);
		alerts = new ArrayList<String>();
		BookedFlights = new ArrayList<Flight>();
		StandbyFlights = new ArrayList<Flight>();
	}
	
	private String setName (String name)
	{
		if(name == null)
			throw new RuntimeException("No null names allowed!");
		else if(name.trim().equals(""))
		{
			throw new RuntimeException("The name cannot be empty!");
		}
		else
		return name;
	}
	
	public String getFirstName()
	{
		return First;
	}
	public String getLastName()
	{
		return Last;
	}
	public ArrayList<String> getAlerts()
	{		
		return alerts;
	}
	public ArrayList<Flight> getBookedFlights()
	{
		return BookedFlights;
	}
	public ArrayList<Flight> getStandbyFlights()
	{
		return StandbyFlights;
	}
	
	public boolean bookFlight(Flight f)
	{
		if(f==null) //Handle null case.
			throw new RuntimeException("Flight can not be null.");
		
		if(BookedFlights.contains(f)) // Handle double booking case. 
			throw new RuntimeException("You have already booked this flight.");
		
		boolean success = f.addPassenger(this); // passenger is added to booked-passenger list if possible.
		if(success)
			{
				BookedFlights.add(f); // flight is added to booked-flights list. 
				if(StandbyFlights.contains(f)) // If the flight we are booking was previously a standbyflight.
				{
					f.removeStandbyPassenger(this); // Passenger is removed from standby list and updates count.
					StandbyFlights.remove(f); // Flight is cancelled from (standby) itinerary.
				}
				return true;
			}
		else
			return false; // Otherwise.
		
	}
	
	public boolean addStandbyFlight(Flight f)
	{
		if(f==null) //Handle null case.
			throw new RuntimeException("Flight can not be null.");
		else if(StandbyFlights.contains(f))
			throw new RuntimeException("You are already on stand by for this flight.");
		else if(BookedFlights.contains(f))
			throw new RuntimeException("You are already booked for this flight.");
		else
		{
			StandbyFlights.add(f);
			f.addStandbyPassenger(this); // Adds the Passenger to the Flight’s standby list.
			return true; // Always succeeds. (Given)
		}
	}
	
	void addAlert(String s)
	{
		alerts.add(s);
	}
	
	public void clearAlerts()
	{
		alerts = null;
	}
	
	public void cancelFlight(Flight f)
	{
		if(f==null) //Handle null case.
			throw new RuntimeException("Flight can not be null.");
		
		if(BookedFlights.contains(f))
		{
			BookedFlights.remove(f); // Flight is cancelled from itinerary.
			f.removePassenger(this); // remove from booked-Flight list.
		}
		else if(StandbyFlights.contains(f))
		{
			StandbyFlights.remove(f); // Flight is cancelled from (standby) itinerary.
			f.removeStandbyPassenger(this); // remove from standby-Flight list.
		}
		else
			throw new RuntimeException("This flight is not on booked or standby status for you.");
	}
	
	public void cancelAll()
	{
		for(int i = 0; i<BookedFlights.size(); i++)
		{
			Flight temp = BookedFlights.get(i);
			cancelFlight(temp);
		}
		
		for(int i = 0; i<StandbyFlights.size(); i++)
		{
			Flight temp = StandbyFlights.get(i);
			cancelFlight(temp);
		}
		
	}

}
