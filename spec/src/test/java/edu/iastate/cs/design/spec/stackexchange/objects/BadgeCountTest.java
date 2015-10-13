package edu.iastate.cs.design.spec.stackexchange.objects;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class BadgeCountTest {

    @Test
    public void deserialize() {
        String json = "{\"bronze\": 1,\"silver\": 2,\"gold\": 3}";
        BadgeCountDTO badgeCount = StackExchangeObjectParser.parseBadgeCountObject(json);
        assertEquals(1, badgeCount.getBronze());
        assertEquals(2, badgeCount.getSilver());
        assertEquals(3, badgeCount.getGold());
    }
}
