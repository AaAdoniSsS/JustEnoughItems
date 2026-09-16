package mezz.jei.gui.bookmarks.tree;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RecipeTreeSidebarClickTest {
	private final RecipeTreeSidebarClick<String> clicks = new RecipeTreeSidebarClick<>();
	private final List<String> lookups = new ArrayList<>();
	private final Object slot = new Object();

	private boolean release(Object target, double x, double y, int button) {
		return clicks.release(target, x, y, button, (ingredient, action) -> lookups.add(ingredient + ":" + action));
	}

	@Test
	void leftClickLooksUpRecipeOnlyOnRelease() {
		assertTrue(clicks.press(slot, "item", 10, 20, 0, false));
		assertTrue(lookups.isEmpty());
		assertTrue(release(slot, 10, 20, 0));
		assertEquals(List.of("item:SHOW_RECIPE"), lookups);
		assertFalse(release(slot, 10, 20, 0));
	}

	@Test
	void rightClickLooksUpUsesAndPreservesIngredient() {
		assertTrue(clicks.press(slot, "fluid", 10, 20, 1, false));
		assertTrue(release(slot, 10, 20, 1));
		assertEquals(List.of("fluid:SHOW_USES"), lookups);
	}

	@Test
	void ignoresUnsupportedButtonsAndModifiedClicks() {
		for (int button : new int[]{-1, 2, 3}) {
			assertFalse(clicks.press(slot, "item", 10, 20, button, false));
			assertFalse(release(slot, 10, 20, button));
		}
		for (int button : new int[]{0, 1}) {
			assertFalse(clicks.press(slot, "item", 10, 20, button, true));
			assertFalse(release(slot, 10, 20, button));
		}
		assertTrue(lookups.isEmpty());
	}

	@Test
	void emptyAreaCannotStartLookup() {
		assertFalse(clicks.press(null, "item", 10, 20, 0, false));
		assertFalse(release(slot, 10, 20, 0));
		assertTrue(lookups.isEmpty());
	}

	@Test
	void allowsSmallPointerJitter() {
		clicks.press(slot, "item", 10, 20, 0, false);
		clicks.move(12, 22);
		assertTrue(release(slot, 12, 22, 0));
		assertEquals(List.of("item:SHOW_RECIPE"), lookups);
	}

	@Test
	void dragStaysCancelledEvenWhenPointerReturns() {
		clicks.press(slot, "item", 10, 20, 0, false);
		clicks.move(20, 20);
		clicks.move(10, 20);
		assertTrue(release(slot, 10, 20, 0));
		assertTrue(lookups.isEmpty());
	}

	@Test
	void checksDistanceOnReleaseEvenWithoutDragEvent() {
		clicks.press(slot, "item", 10, 20, 0, false);
		assertTrue(release(slot, 15, 20, 0));
		assertTrue(lookups.isEmpty());
	}

	@Test
	void releaseOutsideOrOnAnotherSlotIsConsumedWithoutLookup() {
		for (Object target : new Object[]{null, new Object()}) {
			clicks.press(slot, "item", 10, 20, 0, false);
			assertTrue(release(target, 10, 20, 0));
		}
		assertTrue(lookups.isEmpty());
	}

	@Test
	void equalIngredientsInDifferentCellsDoNotCountAsTheSameTarget() {
		Object first = new String("same ingredient"), second = new String("same ingredient");
		clicks.press(first, "item", 10, 20, 0, false);
		assertTrue(release(second, 10, 20, 0));
		assertTrue(lookups.isEmpty());
	}

	@Test
	void scrollOrModifierCancellationConsumesReleaseAndNextClickWorks() {
		clicks.press(slot, "item", 10, 20, 0, false);
		clicks.cancel();
		assertTrue(release(slot, 10, 20, 0));
		assertTrue(lookups.isEmpty());
		clicks.press(slot, "item", 10, 20, 0, false);
		release(slot, 10, 20, 0);
		assertEquals(List.of("item:SHOW_RECIPE"), lookups);
	}

	@Test
	void unrelatedButtonReleaseCannotActivatePendingLookup() {
		clicks.press(slot, "item", 10, 20, 0, false);
		assertFalse(release(slot, 10, 20, 1));
		assertTrue(lookups.isEmpty());
		assertTrue(release(slot, 10, 20, 0));
		assertEquals(List.of("item:SHOW_RECIPE"), lookups);
	}

	@Test
	void screenChangeDropsPendingGesture() {
		clicks.press(slot, "item", 10, 20, 0, false);
		clicks.reset();
		assertFalse(release(slot, 10, 20, 0));
		assertTrue(lookups.isEmpty());
	}

	@Test
	void clearsPendingStateBeforeOpeningAnotherScreen() {
		clicks.press(slot, "item", 10, 20, 0, false);
		assertTrue(clicks.release(slot, 10, 20, 0, (ingredient, action) -> {
			assertFalse(release(slot, 10, 20, 0));
			lookups.add(ingredient);
		}));
		assertEquals(List.of("item"), lookups);
	}
}
