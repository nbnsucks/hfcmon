
Discussion forum
---------------------------------------------------------

Feel free to [send me a private message](https://forums.whirlpool.net.au/user/853268).

Alternatively, good forums to discuss your HFC issues are;
- [HFC Network - General Discussion](https://forums.whirlpool.net.au/thread/9271vnm3?p=-1)
- [NBN HFC Arris modem status page](https://forums.whirlpool.net.au/thread/90ym1z23?p=-1)

How to contribute
---------------------------------------------------------

Feel free to fork this repository and send me pull requests.

HFC Overview
=========================================================

HFC stands for *"Hardly Fucking Connected"* not *"Hybrid Fibre Coaxial"* like NBN would like to have you believe.
HFC is an outdated and failing cable infastructure bought by NBN from Telstra for $11 billion and Optus for $800 million,
used instead of replacing this aging and rusting infastructure with *Fibre to the Premise (FFTP)* like the government initially promised.

The HFC infastructure is prone to failures. Telstra technicians used to work diligently in the background to keep the cable infastructure working.
However the picture is quite different with NBN. NBN doesn't appear to be actively monitoring or maintaining the HFC network in any way.
Instead a customer has to complain for NBN to become slightly aware there might be a problem.
The majority of customers don't have the technical expertise to convince their Retail Service Provider (RSP) to contact NBN to report faults.
The overall result is an increasingly poor internet experience for Australians.

Telstra chairman John Mullen has claimed all Australians would have access to high-speed internet at a "fraction of the cost" if the government had not proceeded with the NBN monopoly.
The Telstra Chairman said:
  > "It is my view that over the last 10 years private sector competition between strong players such as Telstra, Optus, TPG and others was always going to build 100 Mbps broadband access
  > and speed to the majority of the population of Australia, in an ongoing competitive landscape and at no cost whatsoever to the taxpayer,"

And I think he's right. Unfortunately we seem to be stuck with the NBN monopoly,
so lets instead discuss how to stablise and fix your NBN HFC connection.


Having issues with your NBN HFC connection?
---------------------------------------------------------

There's a lot of technical information on this page that can help you understand your issue.
There's also a tool called `hfcmon` that can collect technical information from your cable modem to help you understand your issue better.

Unfortunately though you're probably going to have to complain to your RSP (Retail Service Provider) and get them to contact NBN.
If nothing gets done, as per the [NBN complaint guide](https://whirlpool.net.au/wiki/NBN_complaint_guide),
[register a complaint with the TIO](https://www.tio.com.au/making-a-complaint) and notify your RSP of the TIO reference number.
Good luck! (you're unfortunately probably going to need it)


What can be done to improve this situation?
---------------------------------------------------------

If you're annoyed, take the time to reach out and complain to your Federal MP. I complained to my Federal MP - <a href="https://www.aph.gov.au/Senators_and_Members/Parliamentarian?MPID=LTU">Ged Kearney</a> - with [this email](doc/templates/email-template.md). I eventually received this response;
> Just wanted to send you a quick note on your policy suggestions – which were very thorough and well considered, thanks very much for them. You make some good points – a lack of adequate communication, and the difficulty with which it occurs is a glaring issue with the NBN, and is often paired with the issue of having too many parties engaged in a single household’s issue. I think you make some good points in this regard, and also highlight some common sense solutions to the common issues.
> 
> There are of course a myriad of technical issues with the NBN, and Labor’s approach is to try to work with the Government to iron them out. The NBN has been an issue for a number of years, and as such our Shadow Minister for Communications is well versed on the issues. Ged will be sure to raise your suggestions with the Shadow Minister in her next communications with her, and we’ll continue to be briefed on the Shadow Minister’s progress in pushing the Government to fix their broken roll out of the NBN.
> 
> Thanks again for your detailed suggestions and I assure you they’ll be passed on to the Shadow Minister’s office.

This may have ultimately done nothing to actually improve the situation. But it's better to speak up than to do nothing.


NBN policy suggestions
---------------------------------------------------------

The policy suggestions I made were;
- *Require NBN to label which house is connected to which HFC tap* - a majority of residents issues are caused by an NBN sub-contractor not knowing which house is connected to which tap - and repairing the wrong tap (and therefore not actually fixing the residents problem!).
    In other cases, taps not being labeled has caused NBN technicians to simply unplug existing residents connections, rather then spending the time to properly identify which tap isn't actually being used.
- Stop hiring sub-contractors from companies like Service Stream who come to your house claiming to be competent NBN technicians. Recently it seems, though I haven't seen this myself yet, that NBN seem to be employing their own technicians who are much friendly and seem to be doing a *much* better job. This is good news!
- In line with the ACCC recommendations, *encourage competition with NBN* not discourage it.
      Companies like [DGTek](https://www.news.com.au/technology/online/broadband-startup-completes-initial-fibre-rollout-in-parts-of-melbourne/news-story/24ce37c4aa704843abb5b70f5482bcba) should be welcomed - they not only provide better internet for residents, they also provide lots of benefits for the community as a whole such as free internet for schools.
- If you have an existing TIO ticket that the RSP/NBN hasn't resolved within 2 weeks, the TIO has a 6 week waiting list until a mediator can be appointed.
      Once a TIO mediator is appointed, they have no authority to speak to NBN directly - this is absolutely absurd.
      The ombudsman efficiently has no power to resolve NBN disputes - and neither does the customer.
      *The law has to be changed immediately to allow customers with issues with NBN to speak to NBN directly and have the NBN resolve their disputes.*
      I am fed up beyond believe of speaking to my RSP only for them to tell me they're in the process of trying to get a response from NBN.
      I would highly recommend NBN build an online system to manage this where they can communicate to customers accurately about their issues.
      RSPs have no technology experience or knowledge to resolve NBN HFC issues - nor should they.
      *The entire process is absurd because after 2 months of bad internet, I'm yet to hear the results of a single technical analysis that NBN has done.*

	  
Your NBN technician
---------------------------------------------------------

There are 2 types of NBN technicians;
- Contractors: typically paid a lump sum per a job so regularly only interested in doing the bare minimum to get paid and leave your premise.
   Unfortunately these contractors are only trained and authorised to fix the most basic issues like rusted taps and replacing your cable modem.
- NBN employee: only sent out where the initial contractor has provided a report that they were unable to fix a customers connection issue.

If you were lucky enough to convince your RSP to convince NBN to send out a technician, here's what they'll typically check;
1. Check the tap - if it's rusted, replace the tap; (you'll typically see these taps on the power poles outside your house, unless they're underground).
2. Check the network isolator box (grey box on wall outside house);
3. Check the wall socket inside the house;
4. *(Optional)* Replace Arris CM8200 cable modem;
5. At each step, check for signal/power issues.

Unfortunately there are many *"up tap"* issues that your NBN contractor will be unable and unauthorised to fix. The most common *"up tap"* issue that people have - that your NBN contractor isn't authorized to fix - is with the *tap* itself (i.e. with the 8 port or 4 port metal box that your houses coax connection plugs into outside your house). Many of the original Telstra/Optus taps have completely rusted through, but in many cases NBN have had to install new taps to connect all the houses in your street - you want your connection to be plugged into these newer non-rusted taps and in many cases this will completely solve all your connection issues.

For more information on the technology involved in your HFC connection, see [here](doc/pdfs).

How you can diagnoise your own connection
---------------------------------------------------------

1. Get status page of Arris CM8200 cable modem *(details below on how to do this)*.

2. Check power/SNR values are within acceptable values;
  - *Downstream Power*: most places say the ideal is between "-15 to +15dBmV with closer to 0dBmV being better". However, NBN technicians I've spoke to say "between -6 to 9dBmV is acceptable, 0 to 3dBmV is ideal. 15dBmV will result in drop outs."
  - *Downstream SNR (Signal-to-noise ratio)*: +35dB and above is good, with +40dBmV and above being better.
  - *Upstream Power*: between +40dBmV to 54dBmV is good with closer to 40dBmV is better.
  - *Uncorrectables*: Should be as close to 0 as possible. In my own case, where I'm getting a lot of packet loss and drop outs, I often also see a high number of uncorrectables across all downstream channels.
  - Lots more useful information [here](https://pickmymodem.com/signal-levels-docsis-3-03-1-cable-modem/).

3. If your power/SNR values are completely wrong, you've got clear evidence that there's something wrong with your HFC connection and that an NBN technician should be sent out **immediately** to resolve this.

4. Your cable modem sends all of its requests to the *"Cable Modem Termination System"* (CMTS).
    If your power/SNR are acceptable and you've still got issues, it implies there's an *"up tap"* issue between your tap and the CMTS.
    You can see these issues very clearly if you have access to your cable modems event log (details below on how to do this).
    The most likely issue you'll see is a ["T3 time-out"](https://www.google.com/search?q=DOCSIS+T3+timeout) issue.

Attenuators
---------------------------------------------------------

An NBN technician will occassionally install a 3dB or 6dB [attenuator](https://en.wikipedia.org/wiki/Attenuator_(electronics)).
They'll either install this in the network isolator box (grey box on wall outside house) or sometimes just plug it directly into the back of the cable modem.

An attenuator is an electronic device that reduces the power of a signal without appreciably distorting its waveform.
They're a way of resolving ghosting and reflections caused by too higher signal strength.
The aim of installing an attenuator is to reduce your downstream power levels so they're in spec as discussed above or [here](https://pickmymodem.com/signal-levels-docsis-3-03-1-cable-modem/).

The official NBN attenuators seem to be: [Digitek Attenuators F Type - 3dB (part number 08AF03) or 6dB (part number 08AF06)](http://au.digitek.tv/index.php?page=shop.product_details&category_id=87&flypage=shop.flypageDIGI&product_id=289&option=com_virtuemart&Itemid=1).
You shouldn't install an attenuator yourself - they should only be installed by an authorised NBN technician.

Why does my cable modem restart all by itself?
---------------------------------------------------------

There are several reasons why this could be happening;

1. *Power outage / interruption*: Although this hasn't affected me personally, apparently there are places in Australia where the power flutters and causes the cable modem to restart.
  I'd probably recommend using a UPS, replacing the power supply into the cable modem or adding some ferrite around the power cable.

2. *Internet outage*: From personal experience, after roughly 5 minutes without internet, the cable modem will automatically reset itself.
     This has been the behavior for me with multiple cable modems - it's not a flaw of the modem.
     If you look at the event log for your modem, you'll typically see a lot of "T3 time-out" warnings - after about 5 minutes the cable modem gives up and decides to try restarting it's self.
     When the cable modem restarts, you'll often see the cable modem struggle to get a downstream/upstream lock - taking a lot longer than normal to start up - because it continues to keep getting "T3 time-out" issues. NBN don't monitor for "T3 time-out" or similar other issues (although they're technically present in the "Cable Modem Termination System" (CMTS) logs which NBN really should be doing a better job of monitoring and responding to). The only statistic NBN keep and can discuss with you is how many times your cable model is "Flapping". I highly recommend reading ["What is a Cable Modem Flap?"](https://www.techwalla.com/articles/what-is-a-cable-modem-flap) because it's the only semi-intelligent metric NBN collect. If the flap count is increasing a lot each day, it's most likely caused by "T3 time-outs" caused by line issues.

How to get status page of Arris CM8200 cable modem
---------------------------------------------------------

Most people have the following setup;
```
[Their Computer/Phone] -> [Router with NAT] -> [Arris CM8200 cable modem]
```

In order to access your cable modems status page you either need to;
  1. Connect directly to back of `[Arris CM8200 cable modem]` *(easy)*.
  2. Connect via your router - a little more challenging, but very possible!

The `[Arris CM8200 cable modem]` exposes the webpage `http://192.168.100.1` for a very short 1 to 2 minute window after a hard reset.
To hard reset the cable modem, press and hold the reset switch on the back of your cable modem.

Fun, but not entirely useful: the cable modem exposes a Spectrum Analysiser for the same very short window after a hard reset.
It can be accessed by going to `http://admin:password@192.168.100.1:8080` - i.e. `http://192.168.100.1:8080` with username `"admin"` and password `"password"`.
For some people, the login credentials appear to be username `"admin"` and password `"admin"`.
This webpage can be used to determine your [OFDM channel width](https://en.wikipedia.org/wiki/Orthogonal_frequency-division_multiplexing).

Why does the Arris CM8200 webpage disappear? And how can we stop this!
---------------------------------------------------------

After about 1 to 2 minutes, the `192.168.100.1` address disappears (run `arp -a` to see it disappear).
What's happening is the cable modem receives a configuration file from NBN saying that it should no longer advertise the `192.168.100.1` address - however the webserver is still there and running!

Your cable modem has 2 MAC address;
  - the MAC address for it's internal interface - *this is the one you need!*. Example: "50:75:f1:00:00:a0".
  - the MAC address for it's external interface - also called the "Cable Modem MAC Address" or "HFC MAC Address" - it's the MAC address written on the bottom of your cable modem and it's *not* the one you need!. Example: "50:75:f1:00:00:a1".

The best way to determine the internal MAC address is to connect directly to the back of your `[Arris CM8200 cable modem]`
and when you successfully connect to `http://192.168.100.1` after a hard reset - run `arp -a` to get the internal MAC address for `192.168.100.1`.
In my case, my internal MAC address was one less than my external MAC address (i.e. one less than the MAC address written on the bottom of my cable modem).

*Now you have this you'll never need to hard reset your cable modem again!*

How to reconnect on windows without having to hard reset; (assumes connected to same network, not behind router)
```
netsh interface ip delete  neighbors "Local Area Connection" "192.168.100.1"
arp -a
...
(try to connect to http://192.168.100.1 - wait for cable modem to either poison arp table with 00-00-00-00-00-00 or for your OS to give up)
...
netsh interface ip add     neighbors "Local Area Connection" "192.168.100.1" 50-75-f1-00-00-a0
netsh interface ip replace neighbors "Local Area Connection" "192.168.100.1" 50-75-f1-00-00-a0
```

How to reconnect on linux without having to hard reset; (assumes connected to same network, not behind router - ideally you should run these commands from your router)
```
ip neighbour delete 192.168.100.1 dev eth0.2
ip neighbour show  ;  arp -a
...
(try to connect to http://192.168.100.1 - wait for cable modem to either poison arp table with 00-00-00-00-00-00 or for your OS to give up)
...
ip neighbour add     192.168.100.1 dev eth0.2 lladdr 50:75:f1:00:00:a0
ip neighbour replace 192.168.100.1 dev eth0.2 lladdr 50:75:f1:00:00:a0
```

If you want to connect via your router - you've got two options;
1. Be able to run the above commands on your router *(easier and reliable option!)*. This requires either having a linux/windows server setup as your router/gateway - or having a router with [openwrt](https://www.makeuseof.com/tag/what-is-openwrt-and-why-should-i-use-it-for-my-router/) or similar installed on it; or
2. Connect very quickly after a hard reset of your cable modem and hope your router hasn't given up trying to figure out the MAC address for `192.168.100.1` and hope your cable modem doesn't reset itself (or you'll have to hard reset again) *(much harder and annoying option that may slowly drive you insane)*.

Other steps you'll need to follow to make the above work;
- Add a private WAN network with a static ip address of 192.168.100.2/255.255.255.0 - do *not* specify any gateway or DNS values.
- On [openwrt](https://www.makeuseof.com/tag/what-is-openwrt-and-why-should-i-use-it-for-my-router/), you'll need to run `opkg install ip-full` to install the full ip utility (otherwise you won't be able to run the `ip neighbour ...` commands above).
- Please make sure your router is secure. For example, if you install [openwrt](https://www.makeuseof.com/tag/what-is-openwrt-and-why-should-i-use-it-for-my-router/), you'll want to make it more secure - very easy to do!
   - On the [Firewall - General Settings](http://192.168.0.1/cgi-bin/luci/admin/network/firewall) tab, by default the firewall is set to REJECT rather than DROP packets (i.e. if you goto [https://www.grc.com/shieldsup](https://www.grc.com/shieldsup) you'll see all your ports are closed instead of stealthed) - so change REJECT to DROP whereever you see it!
   - On the [Firewall - Traffic Rules](http://192.168.0.1/cgi-bin/luci/admin/network/firewall/rules) tab, by default your router is externally pingable - you can fix this and other things, by unchecking every checkbox except "Allow-DHCP-Renew"/"Allow-DHCPv6".

After following the above guide, I went from being able to very rarely connect to my `Arris CM8200 cable modem` to always being able to connect *without ever having to do a hard reset*.

HFC Monitor (hfcmon)
=========================================================

This monitor has 2 functions.
The first is it stays constantly connected to `http://192.168.100.1` and writes your power/SNR values and cable modems event logs to a CSV/JSON file.
The second is it checks if your internet is connected and writes internet outages to a CSV/JSON file.

`hfcmon` is designed to help you correlate internet outages with your cable modems power/SNR values and your cable modems event logs.
You can just look at the `hfcmon` log to diagnoise connectivity issues - see the example section below.
The `hfcmon` log outputs the min/max power/SNR values - and will output `bad!` or `very bad!` beside a power/SNR value if it goes out of spec.

Because `hfcmon` will stay constantly connected to your cable modem after a hard reset,
you'll be able to access the cable modem's status pages at any time - assuming of course your cable modem doesn't
restart - by going to `http://192.168.100.1` in your web-browser.

Prerequisite steps;
  1. Check you can access `http://192.168.100.1` (see section above for help on how to do this).
     *If you can't access this webpage, this app isn't going to have any better luck.*
  2. Install Java 8 or above;
     - Download from https://www.java.com/en/download/
     - Check installed correctly by openning command line and running `"java -version"`. Should print `"java version 1.8.0_221"` or similar.

How to run;
  1. Download [hfcmon.zip](bin/hfcmon.zip) and extract.
  2. Open command line and run `"java -jar hfcmon-0.3.jar"`
  3. Either fix arp cache or perform hard reset of cable modem (described in previous section) - the app will connect to the cable modem as soon as it's available.
  4. You'll start seeing the log lines like `"Internet connected? Yes. Downstream 6 dBmV/41 dB. Upstream 44 dBmV..."` once successfully connected.

Just want to monitor internet stability? Don't have an Arris CM8200 cable modem?
---------------------------------------------------------

In [hfcmon.conf](hfcmon/hfcmon.conf), disable the Arris CM8200 cable modem check functionality;
```
 #
 # Modem Check Enabled
 #
 # If you only want hfcmon to keep a record of when your internet is up/down, then set this to false.
 #
 modem.enabled=false
```

You've now got an awesome tool that will tell you when your internet is up or down!
Makes a descending beep sound when your internet is disconnected and an ascending beep sound when your internet is reconnected - disable this by setting `internetcheck.beep` to `false`.
Outputs internet outages to a `internet-outages.csv` and `internet-outages.json` file - configurable by changing `output.outageCsv` and `output.outageJson`.

For those people who do have an Arris CM8200 cable modem - but it's annoyingly restarting very regularly - you might want to set `modem.enabled` to `false` and just monitor internet outages.

Similar tools
---------------------------------------------------------

[Risbo](https://forums.whirlpool.net.au/user/135897) produces a similar tool: https://github.com/risb0r/Arris-CM8200-to-InfluxDB

Comparison:
  1. *Language:* Risbo's tool is written python. `hfcmon` is written in java. *Java's easy - learn it [here](https://docs.oracle.com/javase/tutorial/java)!*
  2. *Output:* Risbo's tool outputs to InfluxDB (which you'll need to install). `hfcmon` outputs CSV/JSON files.
  3. *Internet connectivity:* Risbo's tool doesn't capture the current state of your internet connection. `hfcmon` pings several hosts (configurable from the [config file](hfcmon/hfcmon.conf))
       and decides that your internet is down if all of these hosts can't be reached.
       `hfcmon` is therefore the better tool if you've got internet connectivity issues.
  4. *Visualisations:* Risbo's tool givens you an awesome dashboard of absolutely every single power/SNR value of your cable modem - see it [here](https://raw.githubusercontent.com/risb0r/Arris-CM8200-to-InfluxDB/master/images/overview.png)!
       `hfcmon` doesn't produce any awesome visualisations like this - however it captures the very same information you could use to produce these visualisations yourself.
	   For example, one quick and dirty option is you could open the CSV files in Excel and produce any graphs you need yourself.

*Summary:* if you want cool visualisations, use Risbo's tool. If you're having internet connectivity issues, use `hfcmon`.

Example outputs from HFC Monitor (hfcmon)
---------------------------------------------------------

I built this app because my RSP and NBN told me there were no issues with my HFC connection.
**Apparently not having internet for 10 to 30 minutes of every hour is an acceptable internet connection.**
The logs below show that despite having acceptable power/SNR values, I'm regularly loosing contact with the *"Cable Modem Termination System"* (CMTS).
This points to an *"up tap"* issue that will require an NBN employee (not an under qualified NBN contractor) to resolve it.

**Unfortunately this situation shows that NBN are not actively monitoring the CMTS's logs.**
The customer should not have to complain, or have enough technical knowledge to diagnose their fault to have it fixed.
However given NBN clearly aren't performing any useful active monitoring or maintenance, we can clearly expect this situation to get worse.

Example output showing a single T3 timeout resulting in internet drop out of 65 seconds;
```
[INFO |2019-10-27 11:30:39.496|ModemCheck] Internet connected? Yes. Downstream 6 dBmV/41 dB. Upstream 44 dBmV.
[INFO |2019-10-27 11:30:54.512|ModemCheck] Internet connected? Yes. Downstream 6 dBmV/41 dB. Upstream 44 dBmV.
[INFO |2019-10-27 11:31:09.482|ModemCheck] Internet connected? Yes. Downstream 6 dBmV/41 dB. Upstream 44 dBmV. 399 packet errors.
[INFO |2019-10-27 11:31:24.498|ModemCheck] Internet connected? Yes. Downstream 5 dBmV/41 dB. Upstream 43 dBmV.
[INFO |2019-10-27 11:31:39.514|ModemCheck] Internet connected? Yes. Downstream 5 dBmV/41 dB. Upstream 43 dBmV.
[INFO |2019-10-27 11:31:47.142|InternetCheck] Internet disconnected
[INFO |2019-10-27 11:31:54.500|ModemCheck] Internet connected? No. Downstream 6 dBmV/41 dB. Upstream 43 dBmV. 16 packet errors.
[INFO |2019-10-27 11:32:09.501|ModemCheck] Internet connected? No. Downstream 6 dBmV/41 dB. Upstream 43 dBmV.
[INFO |2019-10-27 11:32:24.517|ModemCheck] Internet connected? No. Downstream 6 dBmV/41 dB. Upstream 43 dBmV.
[INFO |2019-10-27 11:32:39.501|ModemCheck] Internet connected? No. Downstream 6 dBmV/41 dB. Upstream 43 dBmV.
[INFO |2019-10-27 11:32:52.163|InternetCheck] Internet reconnected after 1.083 minutes / 65 seconds
[INFO |2019-10-27 11:32:54.531|ModemCheck] Internet connected? Yes. Downstream 6 dBmV/41 dB. Upstream 44 dBmV. 336 packet errors.
[INFO |2019-10-27 11:32:54.531|ModemCheck] Cable modem has new event log:
"Started Unicast Maintenance Ranging - No Response received - T3 time-out;CM-MAC=XX:XX:XX:XX:XX:XX;CMTS-MAC=XX:XX:XX:XX:XX:XX;CM-QOS=1.1;CM-VER=3.1;" (date=10/27/2019 10:32, eventId=82000500, eventLevel=3)
[INFO |2019-10-27 11:33:09.519|ModemCheck] Internet connected? Yes. Downstream 5 dBmV/41 dB. Upstream 44 dBmV.
[INFO |2019-10-27 11:33:24.520|ModemCheck] Internet connected? Yes. Downstream 5 dBmV/41 dB. Upstream 44 dBmV.
[INFO |2019-10-27 11:33:39.522|ModemCheck] Internet connected? Yes. Downstream 5 dBmV/41 dB. Upstream 44 dBmV.
```

Much worse example where internet is down for about 5 minutes, and as a result of T3 timeouts, performs an automatic restart (meaning hfcmon can no longer connect to your cable modem);
```
[INFO |2019-10-27 12:35:10.335|ModemCheck] Internet connected? Yes. Downstream 7 dBmV/41 dB. Upstream 41 dBmV.
[INFO |2019-10-27 12:35:25.397|ModemCheck] Internet connected? Yes. Downstream 7 dBmV/41 dB. Upstream 41 dBmV.
[INFO |2019-10-27 12:35:40.320|ModemCheck] Internet connected? Yes. Downstream 7 dBmV/41 dB. Upstream 41 dBmV.
[INFO |2019-10-27 12:35:55.383|ModemCheck] Internet connected? Yes. Downstream 6 dBmV/41 dB. Upstream 41 dBmV.
[INFO |2019-10-27 12:36:02.641|InternetCheck] Internet disconnected
[INFO |2019-10-27 12:36:10.368|ModemCheck] Internet connected? No. Downstream 6 dBmV/41 dB. Upstream 41 dBmV.
[INFO |2019-10-27 12:36:10.368|ModemCheck] Cable modem has new event log:
"Started Unicast Maintenance Ranging - No Response received - T3 time-out;CM-MAC=XX:XX:XX:XX:XX:XX;CMTS-MAC=XX:XX:XX:XX:XX:XX;CM-QOS=1.1;CM-VER=3.1;" (date=10/27/2019 11:36, eventId=82000500, eventLevel=3)
[INFO |2019-10-27 12:36:25.338|ModemCheck] Internet connected? No. Downstream 5 dBmV/41 dB. Upstream 41 dBmV.
[INFO |2019-10-27 12:36:35.675|InternetCheck] Internet reconnected after 0.55 minutes / 33 seconds
[INFO |2019-10-27 12:36:40.376|ModemCheck] Internet connected? Yes. Downstream 5 dBmV/41 dB. Upstream 41 dBmV.
[INFO |2019-10-27 12:36:43.642|InternetCheck] Internet disconnected
...
[INFO |2019-10-27 12:40:10.700|ModemCheck] Internet connected? No. Downstream 6 dBmV/41 dB. Upstream 42 dBmV.
[INFO |2019-10-27 12:40:25.700|ModemCheck] Internet connected? No. Downstream 6 dBmV/41 dB. Upstream 42 dBmV.
[INFO |2019-10-27 12:40:40.670|ModemCheck] Internet connected? No. Downstream 6 dBmV/41 dB. Upstream 42 dBmV.
[INFO |2019-10-27 12:40:55.740|ModemCheck] Internet connected? No. Downstream 6 dBmV/41 dB. Upstream 43 dBmV.
[INFO |2019-10-27 12:40:55.740|ModemCheck] Cable modem has new event log:
"No Ranging Response received - T3 time-out;CM-MAC=XX:XX:XX:XX:XX:XX;CMTS-MAC=XX:XX:XX:XX:XX:XX;CM-QOS=1.1;CM-VER=3.1;" (date=10/27/2019 11:40, eventId=82000200, eventLevel=3)
[INFO |2019-10-27 12:41:10.774|ModemCheck] Internet connected? No. Downstream 6 dBmV/41 dB. Upstream 43 dBmV.
[INFO |2019-10-27 12:41:10.774|ModemCheck] Cable modem has new event logs:
"No Ranging Response received - T3 time-out;CM-MAC=XX:XX:XX:XX:XX:XX;CMTS-MAC=XX:XX:XX:XX:XX:XX;CM-QOS=1.1;CM-VER=3.1;" (date=10/27/2019 11:41, eventId=82000200, eventLevel=3)
"Ranging Request Retries exhausted;CM-MAC=XX:XX:XX:XX:XX:XX;CMTS-MAC=XX:XX:XX:XX:XX:XX;CM-QOS=1.1;CM-VER=3.1;" (date=10/27/2019 11:41, eventId=82000300, eventLevel=3)
"16 consecutive T3 timeouts while trying to range on upstream channel 2;CM-MAC=XX:XX:XX:XX:XX:XX;CMTS-MAC=XX:XX:XX:XX:XX:XX;CM-QOS=1.1;CM-VER=3.1;" (date=10/27/2019 11:41, eventId=82000800, eventLevel=3)
[INFO |2019-10-27 12:41:25.775|ModemCheck] Internet connected? No. Downstream 6 dBmV/41 dB. Upstream 41 dBmV.
[INFO |2019-10-27 12:41:25.775|ModemCheck] Cable modem has new event log:
"No Ranging Response received - T3 time-out;CM-MAC=XX:XX:XX:XX:XX:XX;CMTS-MAC=XX:XX:XX:XX:XX:XX;CM-QOS=1.1;CM-VER=3.1;" (date=10/27/2019 11:41, eventId=82000200, eventLevel=3)
[INFO |2019-10-27 12:41:40.885|ModemCheck] Internet connected? No. Downstream 6 dBmV/41 dB. Upstream 38 dBmV.
[INFO |2019-10-27 12:41:55.886|ModemCheck] Internet connected? No. Downstream 6 dBmV/41 dB. Upstream 39 dBmV.
...
(cable modem automatically restarts)
...
[WARN |2019-10-27 12:42:07.408|Http] Failed to connect to "http://192.168.100.1"; java.net.SocketTimeoutException: connect timed out
[WARN |2019-10-27 12:42:09.421|Http] Failed to connect to "http://192.168.100.1"; java.net.SocketTimeoutException: connect timed out
...
[WARN |2019-10-27 12:44:04.128|Http] Failed to connect to "http://192.168.100.1"; java.net.SocketTimeoutException: connect timed out
[WARN |2019-10-27 12:44:06.140|Http] Failed to connect to "http://192.168.100.1"; java.net.SocketTimeoutException: connect timed out
[INFO |2019-10-27 12:44:06.171|InternetCheck] Internet reconnected after 7.36 minutes / 442 seconds
[WARN |2019-10-27 12:44:08.155|Http] Failed to connect to "http://192.168.100.1"; java.net.SocketTimeoutException: connect timed out
[WARN |2019-10-27 12:44:10.168|Http] Failed to connect to "http://192.168.100.1"; java.net.SocketTimeoutException: connect timed out
...
(internet connection re-established - but can't connect to cable modem unless you fix arp cache or perform a hard reset)
```
