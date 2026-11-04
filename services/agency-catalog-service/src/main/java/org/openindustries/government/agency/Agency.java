package org.openindustries.government.agency;

import java.util.UUID;

/** Agency aggregate — Catalogue of public agencies and their service counts. */
public class Agency {
    public UUID id;
        public String name;
        public String level;
        public int servicesCount;
    public String status;

    public static Agency create(String name, String level, int servicesCount) {
        Agency e = new Agency();
        e.id = UUID.randomUUID();
        e.name = name;
        e.level = level;
        e.servicesCount = servicesCount;
        e.status = "ACTIVE";
        return e;
    }
}
