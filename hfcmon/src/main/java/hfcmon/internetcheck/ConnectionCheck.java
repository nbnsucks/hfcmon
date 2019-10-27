package hfcmon.internetcheck;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import hfcmon.utils.Logger;
import hfcmon.utils.LoggerFactory;
import hfcmon.utils.UnexpectedException;

/**
 * Performs a single ping to check if the internet is connected.
 * If that single ping fails, attempts to ping all domains provided - if none respond, we conclude the internet is down.
 * 
 * On linux, InetAddress.isReachable() either needs `root` or the `cap_net_raw` capability.
 * 
 * Can give `cap_net_raw` capability with;
 *   setcap cap_net_raw+eip $JAVA_HOME/bin/java
 *   ln -s $JAVA_HOME/lib/amd64/jli/libjli.so /usr/lib64/libjli.so
 *   Please note: just doing `export LD_LIBRARY_PATH=$JAVA_HOME/lib/amd64/jli` doesn't work.
 */
public final class ConnectionCheck {

    private final List<Pingable> all = new ArrayList<Pingable>();
    private final List<Pingable> alive = new ArrayList<Pingable>();
    private int index = -1;

    protected ConnectionCheck(LoggerFactory factory, int timeout, String... domains) {
        Logger logger = factory.getLogger(this);
        for (String domain : domains) {
            all.add(new Pingable(logger, domain, timeout));
        }
        alive.addAll(all);
    }

    protected boolean isConnected() {
        // Ensure we have some alive
        List<Pingable> alive = this.alive;
        int aliveCount = alive.size();
        if (aliveCount <= 0) {
            alive.addAll(this.all);
            aliveCount = alive.size();
        }

        // Ping
        int index = (this.index + 1) % aliveCount;
        this.index = index;
        Pingable pingable = alive.get(index);
        if (pingable.ping()) {
            return true;
        } else {
            List<Pingable> all = this.all;
            for (Pingable curr : this.all) {
                if (curr == pingable) {
                    continue;
                } else if (curr.ping()) {
                    // Remove dead pingable
                    if (alive.remove(index) != pingable) {
                        throw new UnexpectedException("Removed wrong pingable from list");
                    }
                    return true;
                }
            }

            // All pingables are dead - we assume we're not connected - restore all pingables
            if (aliveCount != all.size()) {
                alive.clear();
                alive.addAll(this.all);
            }
            return false;
        }
    }

    private static final class Pingable {
        private final Logger logger;
        private final String host;
        private final int timeout;
        private InetAddress address;

        protected Pingable(Logger logger, String host, int timeout) {
            this.logger = logger;
            this.host = host;
            this.timeout = timeout;
        }

        protected boolean ping() {
            try {
                InetAddress address = this.address;
                if (address == null) {
                    this.address = address = InetAddress.getByName(host);
                }
                return address.isReachable(timeout);
            } catch (Throwable e) {
                logger.warn("Failed to ping \"" + host + "\": " + e);
                return false;
            }
        }

        @Override
        public String toString() {
            StringBuilder buffer = new StringBuilder();
            append(buffer);
            return buffer.toString();
        }

        private void append(StringBuilder buffer) {
            buffer.append(Pingable.class.getSimpleName()).append('(').append(host);
            InetAddress address = this.address;
            if (address != null) {
                buffer.append(" -> ").append(address.getHostAddress());
            }
            buffer.append(')');
        }
    }

}
