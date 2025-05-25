@Test
void testValidItem() {
    Item item = new Item("Test Item", "Description", 1.0);
    assertDoesNotThrow(item::validate);
}

@Test
void testInvalidLabel() {
    Item item = new Item("", "Description", 1.0);
    Exception exception = assertThrows(IllegalStateException.class, item::validate);
    assertEquals("Label cannot be empty.", exception.getMessage());
}

@Test
void testInvalidWeight() {
    Item item = new Item("Test Item", "Description", 0.0);
    Exception exception = assertThrows(IllegalStateException.class, item::validate);
    assertEquals("Weight must be positive.", exception.getMessage());
}