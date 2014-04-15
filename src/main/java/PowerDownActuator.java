/*
 * Copyright (c) 2014 INRIA
 *
 * This file is part of btrplace.
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

import btrplace.executor.Actuator;
import btrplace.executor.ExecutorException;
import btrplace.plan.event.Action;
import btrplace.plan.event.ShutdownNode;

public class PowerDownActuator implements Actuator {

    private String ipAddress;
    private String username;
    private String password;
    private String privilege;
    private int port;
    private ShutdownNode action;

    public PowerDownActuator(BootNode action, String ipAddress,
                             String username, String password,
                             String privilege, int port) {

        this.action = action;
        this.ipAddress = ipAddress;
        this.username = username;
        this.password = password;
        this.privilege = privilege;
        this.port = port;
    }

    @Override
    public void execute() throws ExecutorException {

        IpmiChassisControl ipmiCC = new IpmiChassisControl(ipAddress, username,
                password, privilege, port);

        try {
            ipmiCC.chassisControlActionPowerDown();
        } catch (Exception e) {
            throw new ExecutorException(this, e);
        }
    }

    @Override
    public Action getAction() { return action; }
}