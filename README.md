
Discussion forum
---------------------------------------------------------

To discuss this tool, head over to the `hfcmon` whirlpool forum:

https://forums.whirlpool.net.au/thread/3wj4jy49

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

Unfortunately there are many *"up tap"* issues that your NBN contractor will be unable and unauthorised to fix.

For more information on the technology involved in your HFC connection, see [here](doc).

How you can diagnoise your own connection
---------------------------------------------------------

1. Get status page of Arris CM8200 cable modem *(details below on how to do this)*.

2. Check power/SNR values are within acceptable values;
  - *Downstream Power*: most places say the ideal is between "-15 to +15dBmV with closer to 0dBmV being better". However, NBN technicians I've spoke to say "between -6 to 9dBmV is acceptable, 0 to 3dBmV is ideal. 15dBmV will result in drop outs."
  - *Downstream SNR (Signal-to-noise ratio)*: +35dB and above is good, with +40dBmV and above being better.
  - *Upstream Power*: between +40dBmV to 54dBmV is good with closer to 40dBmV is better.
  - *Uncorrectables*: Should be as close to 0 as possible. In my own case, where I'm getting a lot of packet loss and drop outs, I often also see a high number of uncorrectables across all downstream channels.

3. If your power/SNR values are completely wrong, you've got clear evidence that there's something wrong with your HFC connection and that an NBN technician should be sent out **immediately** to resolve this.

4. Your cable modem sends all of its requests to the *"Cable Modem Termination System"* (CMTS).
    If your power/SNR are acceptable and you've still got issues, it implies there's an *"up tap"* issue between your tap and the CMTS.
    You can see these issues very clearly if you have access to your cable modems event log (details below on how to do this).
    The most likely issue you'll see is a ["T3 time-out"](https://www.google.com/search?q=DOCSIS+T3+timeout) issue.

Why does my cable modem restart all by itself?
---------------------------------------------------------

There are several reasons why this could be happening;

1. *Power outage / interruption*: Although this hasn't affected me personally, apparently there are places in Australia where the power flutters and causes the cable modem to restart.
  I'd probably recommend using a UPS, replacing the power supply into the cable modem or adding some ferrite around the power cable.

2. *Internet outage*: From personal experience, after roughly 5 minutes without internet, the cable modem will automatically reset itself.
     This has been the behavior for me with multiple cable modems - it's not a flaw of the modem.
     If you look at the event log for your modem, you'll typically see a lot of "T3 time-out" warnings - after about 5 minutes the cable modem gives up and decides to try restarting it's self.
     When the cable modem restarts, you'll often see the cable modem struggle to get a downstream/upstream lock - taking a lot longer than normal to start up - because it continues to keep getting "T3 time-out" issues.

How to get status page of Arris CM8200 cable modem
---------------------------------------------------------

Most people have the following setup;
```
[Their Computer/Phone] -> [Router with NAT] -> [Arris CM8200 cable modem]
```

In order to access your cable modems status page you either need to;
  1. Connect directly to back of `[Arris CM8200 cable modem]` *(easy)*.
  2. Add static route to `[Router with NAT]` so can access cable modem without having to plug directly into it *(advanced, not covered here)*.

The `[Arris CM8200 cable modem]` exposes the webpage `http://192.168.100.1` for a very short 1 to 2 minute window after a hard reset.
To hard reset the cable modem, press and hold the reset switch on the back of your cable modem.

Fun, but not entirely useful: the cable modem exposes a Spectrum Analysiser for the same very short window after a hard reset.
It can be accessed by going to `http://admin:password@192.168.100.1:8080` - i.e. `http://192.168.100.1:8080` with username `"admin"` and password `"password"`.
For some people, the login credentials appear to be username `"admin"` and password `"admin"`.
This webpage can be used to determine your [OFDM channel width](https://en.wikipedia.org/wiki/Orthogonal_frequency-division_multiplexing).

Why does the Arris CM8200 webpage disappear? And how can we stop this!
---------------------------------------------------------

After about 1 to 2 minutes the cable modem, the `192.168.100.1` address disappears (run `arp -a` to see it disappear).

This ultimately means you're unable to access the status page that would give you useful information to diagnoise your connection problems.
I've asked NBN repeatedly if they can modify this configuration file to stop hiding this helpful status page, but I've gotten no response.

Thankfully it is possible to keep this webserver active - you just need an application to constantly send requests to it!
Unfortunately if the cable modem restarts for any reason, or you stop sending requests to it constantly,
this webserver will disappear after a very short window and you'll need to perform a hard reset of the cable modem to get it back.

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
  1. Check you can access `http://192.168.100.1` after performing a hard reset.
     *If you can't access this webpage, this app isn't going to have any better luck.*
  2. Install Java 8 or above;
     - Download from https://www.java.com/en/download/
     - Check installed correctly by openning command line and running `"java -version"`. Should print `"java version 1.8.0_221"` or similar.

How to run;
  1. Download [hfcmon-0.2.jar](bin/hfcmon-0.2.jar) and [hfcmon.conf](bin/hfcmon.conf) to same directory.
  2. Open command line and run `"java -jar hfcmon-0.2.jar"`
  3. Perform hard reset of cable modem - the app will connect to the cable modem as soon as it's available.
  4. You'll start seeing the log lines like `"Internet connected? Yes. Downstream 6 dBmV/41 dB. Upstream 44 dBmV..."` once successfully connected.

Just want to monitor internet stability? Don't have an Arris CM8200 cable modem?
---------------------------------------------------------

In [hfcmon.conf](bin/hfcmon.conf), disable the Arris CM8200 cable modem check functionality;
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
  3. *Internet connectivity:* Risbo's tool doesn't capture the current state of your internet connection. `hfcmon` pings several hosts (configurable from the [config file](bin/hfcmon.conf))
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
(internet connection re-established - but can't connect to cable modem unless you perform a hard reset)
```
