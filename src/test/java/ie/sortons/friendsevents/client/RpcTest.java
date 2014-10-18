package ie.sortons.friendsevents.client;

import java.util.HashMap;

import org.junit.Test;

import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.kfuntak.gwt.json.serialization.client.HashMapSerializer;
import com.kfuntak.gwt.json.serialization.client.Serializer;
import ie.sortons.gwtfbplus.shared.domain.fql.FqlEvent;

public class RpcTest extends GWTTestCase {

	public String getModuleName() {
		return "ie.sortons.friendsevents.FriendsEvents";
	}

	//@Ignore
	@Test
	public void testResponse() {

		String s1 = "{\"name\":\"The Irish Sessions at the Grey Horse Tavern\", \"location\":\"Grey Horse Tavern\", \"venue\":{\"latitude\":\"40.746088409544\", \"longitude\":\"-73.05055119397\", \"city\":\"Bayport\", \"state\":\"NY\", \"country\":\"United States\", \"id\":\"34273524066\", \"street\":\"291 Bayport Ave\", \"zip\":\"11705\"}, \"eid\":\"759017490822628\", \"start_time\":\"2014-08-28T08:00:00-0400\", \"end_time\":null, \"is_date_only\":false}";

		String s2 = "	{\"name\":\"Musici Ireland NCH Lunchtime concert\", \"location\":\"National Concert Hall, Dublin, Ireland\", \"venue\":{\"latitude\":\"53.334723253709\", \"longitude\":\"-6.258457361694\", \"city\":\"Dublin\", \"state\":\"\", \"country\":\"Ireland\", \"id\":\"51076645906\", \"street\":\"Earlsfort Terrace\", \"zip\":\"Dublin 2\"}, \"eid\":\"828697223830576\", \"start_time\":\"2014-08-28T13:05:00+0100\", \"end_time\":\"2014-08-28T14:00:00+0100\", \"is_date_only\":false}";

		String s3 = "{\"name\":\"Jazz'in de gracht 2014\", \"location\":\"Jazz in de Gracht\", \"venue\":{\"latitude\":\"52.073503564425\", \"longitude\":\"4.3158850038918\", \"city\":\"The Hague\", \"state\":\"\", \"country\":\"Netherlands\", \"id\":\"288391047843036\", \"street\":\"Dunne Bierkade/Groenewegje\", \"zip\":\"2512 BC\"}, \"eid\":\"810186445688091\", \"start_time\":\"2014-08-28T16:00:00+0100\", \"end_time\":\"2014-08-30T22:00:00+0100\", \"is_date_only\":false}";

		String s4 = "{\"name\":\"AEPi Fall Rush 2014\", \"location\":\"Alpha Epsilon Pi - UC Berkeley\", \"venue\":{\"latitude\":\"37.866631\", \"longitude\":\"-122.252068\", \"city\":\"Berkeley\", \"state\":\"CA\", \"country\":\"United States\", \"id\":\"156805041140194\", \"street\":\"2430 Piedmont Ave\", \"zip\":\"94704\"}, \"eid\":\"776625582388763\", \"start_time\":\"2014-08-28T08:00:00-0700\", \"end_time\":\"2014-09-04T23:30:00-0700\", \"is_date_only\":false}";

		String s5 = "{\"name\":\"[ABA] Recruitment Week | Fall 2014\", \"location\":\"Upper Sproul ABA Table \", \"venue\":{\"name\":\"Upper Sproul ABA Table \"}, \"eid\":\"746225382111601\", \"start_time\":\"2014-08-28T08:00:00-0700\", \"end_time\":\"2014-09-05T22:00:00-0700\", \"is_date_only\":false}";

		String s6 = "{\"name\":\"Baum Haus Comedy Open Air, DJ's & Street Food Event\", \"location\":\"Griessmuehle\", \"venue\":{\"latitude\":\"52.476367266444\", \"longitude\":\"13.458002587537\", \"city\":\"Neukölln\", \"state\":\"\", \"country\":\"Germany\", \"id\":\"170132116381853\", \"street\":\"Sonnenallee 221\", \"zip\":\"12059\"}, \"eid\":\"883704218324765\", \"start_time\":\"2014-08-28T18:00:00+0200\", \"end_time\":\"2014-08-30T01:00:00+0200\", \"is_date_only\":false}";

		String s7 = "{\"name\":\"Pony Girl :: 2014 EAST COAST TOUR\", \"location\":\"Canada\", \"venue\":{\"latitude\":\"60\", \"longitude\":\"-95\", \"street\":\"\", \"zip\":\"\", \"id\":\"107480665948163\"}, \"eid\":\"1519146768302204\", \"start_time\":\"2014-08-28T12:00:00-0400\", \"end_time\":\"2014-09-12T12:00:00-0400\", \"is_date_only\":false}";

		String s8 = "{\"name\":\"The Website Shop turns 1\", \"location\":\"9-11 Harold's Cross Road, Dublin 6\", \"venue\":{\"name\":\"9-11 Harold's Cross Road, Dublin 6\"}, \"eid\":\"553964904725859\", \"start_time\":\"2014-08-28T18:00:00+0100\", \"end_time\":null, \"is_date_only\":false}";

		String s9 = "{\"name\":\"'Behind Bushes' by Fiona Burke - Opening\", \"location\":\"Talbot Gallery and Studios\", \"venue\":{\"latitude\":\"53.351344833899\", \"longitude\":\"-6.2501989177372\", \"city\":\"Dublin\", \"state\":\"\", \"country\":\"Ireland\", \"id\":\"380381850862\", \"street\":\"51 Talbot Street\", \"zip\":\"1\"}, \"eid\":\"293576790828835\", \"start_time\":\"2014-08-28T18:00:00+0100\", \"end_time\":\"2014-08-28T20:00:00+0100\", \"is_date_only\":false}";

		String s10 = "{\"name\":\"Games Night!\", \"location\":\"Print Room Cafe, UCL\", \"venue\":{\"latitude\":\"51.524026219242\", \"longitude\":\"-0.13320868190556\", \"city\":\"London\", \"state\":\"\", \"country\":\"United Kingdom\", \"id\":\"149649335081846\", \"street\":\"\", \"zip\":\"WC1E 6\"}, \"eid\":\"1508717416011745\", \"start_time\":\"2014-08-28T18:00:00+0100\", \"end_time\":null, \"is_date_only\":false}";

		String aResponse = "{\"0\":{\"name\":\"The Irish Sessions at the Grey Horse Tavern\", \"location\":\"Grey Horse Tavern\", \"venue\":{\"latitude\":\"40.746088409544\", \"longitude\":\"-73.05055119397\", \"city\":\"Bayport\", \"state\":\"NY\", \"country\":\"United States\", \"id\":\"34273524066\", \"street\":\"291 Bayport Ave\", \"zip\":\"11705\"}, \"eid\":\"759017490822628\", \"start_time\":\"2014-08-28T08:00:00-0400\", \"end_time\":null, \"is_date_only\":false}, \"1\":{\"name\":\"Musici Ireland NCH Lunchtime concert\", \"location\":\"National Concert Hall, Dublin, Ireland\", \"venue\":{\"latitude\":\"53.334723253709\", \"longitude\":\"-6.258457361694\", \"city\":\"Dublin\", \"state\":\"\", \"country\":\"Ireland\", \"id\":\"51076645906\", \"street\":\"Earlsfort Terrace\", \"zip\":\"Dublin 2\"}, \"eid\":\"828697223830576\", \"start_time\":\"2014-08-28T13:05:00+0100\", \"end_time\":\"2014-08-28T14:00:00+0100\", \"is_date_only\":false}, \"2\":{\"name\":\"Jazz'in de gracht 2014\", \"location\":\"Jazz in de Gracht\", \"venue\":{\"latitude\":\"52.073503564425\", \"longitude\":\"4.3158850038918\", \"city\":\"The Hague\", \"state\":\"\", \"country\":\"Netherlands\", \"id\":\"288391047843036\", \"street\":\"Dunne Bierkade/Groenewegje\", \"zip\":\"2512 BC\"}, \"eid\":\"810186445688091\", \"start_time\":\"2014-08-28T16:00:00+0100\", \"end_time\":\"2014-08-30T22:00:00+0100\", \"is_date_only\":false}, \"3\":{\"name\":\"AEPi Fall Rush 2014\", \"location\":\"Alpha Epsilon Pi - UC Berkeley\", \"venue\":{\"latitude\":\"37.866631\", \"longitude\":\"-122.252068\", \"city\":\"Berkeley\", \"state\":\"CA\", \"country\":\"United States\", \"id\":\"156805041140194\", \"street\":\"2430 Piedmont Ave\", \"zip\":\"94704\"}, \"eid\":\"776625582388763\", \"start_time\":\"2014-08-28T08:00:00-0700\", \"end_time\":\"2014-09-04T23:30:00-0700\", \"is_date_only\":false}, \"4\":{\"name\":\"[ABA] Recruitment Week | Fall 2014\", \"location\":\"Upper Sproul ABA Table \", \"venue\":{\"name\":\"Upper Sproul ABA Table \"}, \"eid\":\"746225382111601\", \"start_time\":\"2014-08-28T08:00:00-0700\", \"end_time\":\"2014-09-05T22:00:00-0700\", \"is_date_only\":false}, \"5\":{\"name\":\"Baum Haus Comedy Open Air, DJ's & Street Food Event\", \"location\":\"Griessmuehle\", \"venue\":{\"latitude\":\"52.476367266444\", \"longitude\":\"13.458002587537\", \"city\":\"Neukölln\", \"state\":\"\", \"country\":\"Germany\", \"id\":\"170132116381853\", \"street\":\"Sonnenallee 221\", \"zip\":\"12059\"}, \"eid\":\"883704218324765\", \"start_time\":\"2014-08-28T18:00:00+0200\", \"end_time\":\"2014-08-30T01:00:00+0200\", \"is_date_only\":false}, \"6\":{\"name\":\"Pony Girl :: 2014 EAST COAST TOUR\", \"location\":\"Canada\", \"venue\":{\"latitude\":\"60\", \"longitude\":\"-95\", \"street\":\"\", \"zip\":\"\", \"id\":\"107480665948163\"}, \"eid\":\"1519146768302204\", \"start_time\":\"2014-08-28T12:00:00-0400\", \"end_time\":\"2014-09-12T12:00:00-0400\", \"is_date_only\":false}, \"7\":{\"name\":\"The Website Shop turns 1\", \"location\":\"9-11 Harold's Cross Road, Dublin 6\", \"venue\":{\"name\":\"9-11 Harold's Cross Road, Dublin 6\"}, \"eid\":\"553964904725859\", \"start_time\":\"2014-08-28T18:00:00+0100\", \"end_time\":null, \"is_date_only\":false}, \"8\":{\"name\":\"'Behind Bushes' by Fiona Burke - Opening\", \"location\":\"Talbot Gallery and Studios\", \"venue\":{\"latitude\":\"53.351344833899\", \"longitude\":\"-6.2501989177372\", \"city\":\"Dublin\", \"state\":\"\", \"country\":\"Ireland\", \"id\":\"380381850862\", \"street\":\"51 Talbot Street\", \"zip\":\"1\"}, \"eid\":\"293576790828835\", \"start_time\":\"2014-08-28T18:00:00+0100\", \"end_time\":\"2014-08-28T20:00:00+0100\", \"is_date_only\":false}, \"9\":{\"name\":\"Games Night!\", \"location\":\"Print Room Cafe, UCL\", \"venue\":{\"latitude\":\"51.524026219242\", \"longitude\":\"-0.13320868190556\", \"city\":\"London\", \"state\":\"\", \"country\":\"United Kingdom\", \"id\":\"149649335081846\", \"street\":\"\", \"zip\":\"WC1E 6\"}, \"eid\":\"1508717416011745\", \"start_time\":\"2014-08-28T18:00:00+0100\", \"end_time\":null, \"is_date_only\":false}}";

		Serializer serializer = (Serializer) GWT.create(Serializer.class);
		
		FqlEvent event1 = (FqlEvent) serializer.deSerialize(s1, "ie.sortons.gwtfbplus.shared.domain.fql.FqlEvent");
		FqlEvent event2 = (FqlEvent) serializer.deSerialize(s2, "ie.sortons.gwtfbplus.shared.domain.fql.FqlEvent");
		FqlEvent event3 = (FqlEvent) serializer.deSerialize(s3, "ie.sortons.gwtfbplus.shared.domain.fql.FqlEvent");
		FqlEvent event4 = (FqlEvent) serializer.deSerialize(s4, "ie.sortons.gwtfbplus.shared.domain.fql.FqlEvent");
		FqlEvent event5 = (FqlEvent) serializer.deSerialize(s5, "ie.sortons.gwtfbplus.shared.domain.fql.FqlEvent");
		FqlEvent event6 = (FqlEvent) serializer.deSerialize(s6, "ie.sortons.gwtfbplus.shared.domain.fql.FqlEvent");
		FqlEvent event7 = (FqlEvent) serializer.deSerialize(s7, "ie.sortons.gwtfbplus.shared.domain.fql.FqlEvent");
		FqlEvent event8 = (FqlEvent) serializer.deSerialize(s8, "ie.sortons.gwtfbplus.shared.domain.fql.FqlEvent");
		FqlEvent event9 = (FqlEvent) serializer.deSerialize(s9, "ie.sortons.gwtfbplus.shared.domain.fql.FqlEvent");
		FqlEvent event10 = (FqlEvent) serializer.deSerialize(s10, "ie.sortons.gwtfbplus.shared.domain.fql.FqlEvent");
		
		HashMapSerializer hashMapSerializer = (HashMapSerializer) GWT.create(HashMapSerializer.class);

		@SuppressWarnings("unchecked")
		HashMap<String, FqlEvent> events = (HashMap<String, FqlEvent>) hashMapSerializer.deSerialize(aResponse,"ie.sortons.gwtfbplus.shared.domain.fql.FqlEvent");

		System.out.println(events.size());
	}
}
