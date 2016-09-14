import java.util.ArrayList;


public class Flight 
{
	private String SourceAirport;
	private String DestinationAirport;
	private int Capacity;
	private int TakeoffTime;
	private int LandingTime;
	
	private int bookedcount;
	
	private int standbycount;
	
	private ArrayList<Passenger> BookedPassengers;
	
	private ArrayList<Passenger> StandbyPassengers;
	
	public Flight(String src, String dest, int takeoffTime, int landingTime, int capacity)
	{
		if(landingTime<=takeoffTime)
			throw new RuntimeException("The flight must take off before it can land.");
		else
		{
			SourceAirport = strcheck(src);
			DestinationAirport = strcheck(dest);
			Capacity = capcheck(capacity);
			TakeoffTime = timecheck(takeoffTime);
			LandingTime = timecheck(landingTime);
			BookedPassengers = new ArrayList<Passenger>();
			StandbyPassengers = new ArrayList<Passenger>();
			
		}
	}
	
	private String strcheck (String name)
	{
		if(name == null)
			throw new RuntimeException("No null input allowed!");
		else if(name.trim().equals(""))
		{
			throw new RuntimeException("The airport name cannot be empty!");
		}
		else if(name.trim().length()!=3)
			throw new RuntimeException("Not a 3 letter airport code.");
		else 
		return name;
	}
	
	private int capcheck (int num)
	{
		if(num<=0)
			throw new RuntimeException("Capacity cannot be negative or zero.");
		else
			return num;
	}
	
	private int timecheck(int time)
	{
		if(time<0 || time>2400)
			throw new RuntimeException("Not within 24 hour range.");
		else if((time%100)>59)
			throw new RuntimeException("Not in proper military time format. Minutes cannot exceed 59.");
		else
			return time;
	}
	
	public String getSourceAirport()
	{
		return SourceAirport;
	}
	public String getDestinationAirport()
	{
		return DestinationAirport;
	}
	public int getCapacity()
	{
		return Capacity;
	}
	public int getTakeoffTime()
	{
		return TakeoffTime;
	}
	public int getLandingTime()
	{
		return LandingTime;
	}
	public ArrayList<Passenger> getBookedPassengers()
	{
		return BookedPassengers;
	}
	public ArrayList<Passenger> getStandbyPassengers()
	{
		return StandbyPassengers;
	}
	
	public boolean addPassenger(Passenger p)
	{
		if(p==null) //Handle null case.
			throw new RuntimeException("Passenger can not be null.");
		
		if(BookedPassengers.contains(p))
			throw new RuntimeException("Passenger is already booked for this flight.");
		
		if(bookedcount<Capacity)
		{
			BookedPassengers.add(p); // Passenger added to booked-passenger list if there is space.
			bookedcount++;
			if(StandbyPassengers.contains(p))
				{removeStandbyPassenger(p);}
			return true;
		}
		else
			return false;
	}
	
	public boolean addStandbyPassenger(Passenger p)
	{
		if(p==null) //Handle null case.
			throw new RuntimeException("Passenger can not be null.");
		else if(StandbyPassengers.contains(p))
			throw new RuntimeException("Passenger is already on stand by for this flight.");
		else if(BookedPassengers.contains(p))
			throw new RuntimeException("Passenger is already booked for this flight.");
		else
		{
		StandbyPassengers.add(p); // Added to standby-passenger list.
		standbycount++;
		return true; // Always succeeds. (Given)
		}
	}
	
	public void removePassenger(Passenger p)
	{
		if(p==null) //Handle null case.
			throw new RuntimeException("Passenger can not be null.");
		
		if(BookedPassengers.contains(p))
		{BookedPassengers.remove(p); // check if p is in list first!
		 bookedcount--; //update count
		}
		else
			throw new RuntimeException("This passenger is not currently booked so they can not be removed.");
	}
	
	public void removeStandbyPassenger(Passenger p)
	{
		if(p==null) //Handle null case.
			throw new RuntimeException("Passenger can not be null.");
		
		if(StandbyPassengers.contains(p))
		{StandbyPassengers.remove(p);
		 standbycount--; //update count
		}
		else
			throw new RuntimeException("This passenger is not currently on the standby list so they can not be removed.");		
	}
	
	public void cancel()
	{
		for(int i = 0; i<BookedPassengers.size(); i++)
		{
			Passenger temp = BookedPassengers.get(i);
			temp.addAlert("Your booked Flight "+SourceAirport+"-"+DestinationAirport+"has been cancelled.");
			temp.cancelFlight(this);
		}
		
		for(int i = 0; i<StandbyPassengers.size(); i++)
		{
			Passenger temp = StandbyPassengers.get(i);
			temp.addAlert("Your standby Flight "+SourceAirport+"-"+DestinationAirport+"has been cancelled.");
			temp.cancelFlight(this);
		}
		// How to filter through all passengers attached to flight: In 2 loops.
		// the alert: passenger.alerts.add();
		// passenger.cancelFlight(this); Cancels this flight for passenger and removes them from Flight lists.

		
	}
	
	public int promotePassengers()
	{
		if(bookedcount<Capacity)
		{
			int tobepromoted;
			if(standbycount<=(Capacity-bookedcount))
				 tobepromoted = standbycount;
			else
				tobepromoted = Capacity-bookedcount;
			for(int i = 0; i<tobepromoted; i++)
			{
				Passenger temp = StandbyPassengers.get(i); // extracting passenger (object) from arraylist and making temporary copy.
				temp.cancelFlight(this); // Flight is cancelled from (standby) itinerary and they are removed from standby-Flight list & count updated.
				temp.bookFlight(this); // 1) Gets this flight on their itinerary 2) Gets them on this flight passenger list & updates count.
				temp.addAlert("You have been moved from standby to booked for the flight "+SourceAirport+"-"+DestinationAirport+".");
				// Told promoted passengers through alert.
			}
			
			return tobepromoted; // return type is int.
		}
		else
			return 0;
	}


}
