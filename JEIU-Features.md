# JEIU Features

Prepared on: 2026-09-14  
Code baselines:  
JEIU 1.21.1, commit `16bd1c611`.  
Official JEI 1.21.1, commit `5e5c21a8f`.

## 1. Bookmarks and Groups

- **Ingredient bookmarks with quantities**: Save ingredients with quantities that can be adjusted later.
- **Recipe bookmarks**: Save complete recipes, including their inputs and outputs.
- **Bookmark group management**: Create groups, include or exclude entries, and move or remove entire groups through drag interactions. Group contents can be expanded or collapsed.
- **Group display and quantity controls**: Switch group display and crafting-related modes, and adjust quantities for individual entries or entire groups, either one unit at a time or using the increment specified in the quantity field.
- **Recipe information markers**: Display recipe markers, crafting station icons, and catalyst markers on bookmarks to distinguish ingredients, recipes, and non-consumable inputs.
- **Bookmark recipe previews and direct transfer**: View recipe previews and ingredient summaries directly from bookmarks. Hold the preview-pinning key to move the cursor into the preview, look up its ingredients, and use the recipe transfer button in supported containers without opening the full recipe screen first.

## 2. Recipe Chains and Supply/Demand Summaries

- **Recipe dependency calculation within groups**: Organize recipes in a bookmark group into a chain, identify final outputs, intermediate products, and external inputs, and calculate ingredient requirements for the target quantities.
- **Supply, demand, and surplus summaries**: Display inputs, outputs, available ingredients, missing ingredients, and surplus amounts in group tooltips. Ordinary ingredient groups can also serve as requirement lists with supply and demand information.
- **Inventory-aware requirements**: Use currently accessible inventory data to calculate how much still needs to be acquired or crafted.
- **Save missing ingredients separately**: Save missing ingredients as a new bookmark group, allowing a plan to be split into separate preparation tasks.
- **Non-consumable ingredient handling**: Allow ingredients to be marked as inputs or catalysts, distinguishing non-consumable ingredients from ordinary consumed ingredients in supported calculations.

## 3. Recipe Tree Visualization

- **Graphical recipe chains**: Display bookmarked recipe chains as nodes and branches, making dependencies between target products and their required ingredients visible.
- **Tree navigation**: Support zooming, panning, fitting the entire tree into view, and searching to locate entries in larger trees.
- **Branch and node inspection**: Expand or collapse branches and inspect the recipe for a single operation and quantity information associated with each node.
- **Requirement views**: Show a branch's actual requirements or the remaining requirements after accounting for an inventory snapshot, with a control to refresh that snapshot.
- **Editing within the tree workflow**: Add recipes to the current tree through the bookmark sidebar, switch ingredient candidates, and refresh recipe chain calculations.

## 4. Favorite Recipes and Preferences

- **Dedicated favorite recipes panel**: Keep frequently used recipes in a separate view from ordinary bookmarks, with controls for viewing, removing, and reordering them.
- **Recipe row display**: Display favorites as recipe rows that can be expanded, collapsed, or shown in different display modes for convenient browsing.
- **Saved output targets and input choices**: For recipes with multiple possible outputs, scroll over the favorite button to switch the target. Saving a favorite preserves the selected target and specified input ingredients for subsequent dependency expansion.
- **One-click recipe tree generation**: Expand ingredient requirements using favorite recipe relationships in one action, reducing the need to add recipes manually at each level.
- **File-based recipe preference rules**: Define preferences through output, input, and recipe conditions to influence recipe selection and display, allowing modpacks to establish consistent ingredient or processing choices.
- **Preference priorities and quick import**: Expressions support logical combinations and semicolon-separated priority levels. Automatic selection of a single recipe requires a unique result, while preference filtering can return multiple results. Files named `recipe-preferences-*.txt` placed in the JEI configuration directory are automatically appended to the main rules file; successfully processed source files are emptied and deleted. The main rules file supports hot reloading.

## 5. Recipe Search and Ingredient Selection

- **Search within the recipe screen**: Further filter recipes within the current recipe lookup.
- **Search by role and field**: Support scopes for inputs (`i:`), outputs (`o:`), processing machines (`c:`), and recipes (`r:`), with matching by name text, tags (`#`), mods (`@`), and resource identifiers (`&`).
- **Combined search conditions**: Support multiple conditions, alternatives separated by `|`, exclusions prefixed with `-`, and quoted text containing spaces. Input-side conditions can also filter ingredient candidates.
- **Search extension integration**: Index recipe search text through JEI's search storage builder API, allowing integration with search extensions such as JECh.
- **Preference-based filtering**: Switch between all, preferred, and non-preferred recipes to focus on recipes that do or do not match the configured rules.
- **Pinned ingredient selection**: After pinning the candidate list, middle-click to select a candidate, with the option to affect only the current slot. Selections are used in subsequent bookmark or transfer operations. Candidates can also be changed with Shift+scroll or Ctrl+Shift+scroll; manually selected candidates stop cycling automatically.
- **Forward and backward recipe navigation**: Preserve lookup navigation history, supporting backward and forward navigation as well as jumps to the first or last entry in the current lookup sequence.

## 6. Recipe Execution and Ingredient Retrieval

- **Recipe overlays and crafting grid filling**: Use a recipe layout as a crafting reference and fill ingredients into supported interfaces, taking selected inputs and target quantities into account.
- **Bookmark-based automatic crafting**: Execute supported crafting recipes for target quantities, with separate actions for crafting the full target amount or only the missing amount. Recipe chains can serve as execution plans.
- **Bookmark ingredient retrieval**: Retrieve all required items or only the missing quantities from supported storage or container interfaces according to bookmark targets.
- **Quantity-aware inventory giving**: In inventory cheat-give mode, use the bookmark quantity when available, otherwise the quantity field. Give only what fits in the inventory, without dropping excess items. Mouse pickup mode retains the one-item or one-stack behavior.

Server requirements: Automatic crafting, extended ingredient retrieval, and quantity-aware inventory giving require corresponding server-side support.

## 7. Integrations with Other Mods

- **AE2 single-recipe pattern encoding**: Encode recipes from the bookmark view into AE2 patterns in supported interfaces.
- **AE2 batch pattern encoding for recipe chains**: Encode patterns in batches from a recipe chain, handle existing or invalid requests, and report encoding results and reasons for stopping.
- **AE2 terminal search and drag-and-drop integration**: Trigger terminal searches from hovered ingredients and use bookmark group drag-and-drop and batch marking operations in supported interfaces.
- **AE2 inventory and crafting integration**: Access network ingredient information through dedicated adapters to support ingredient retrieval, inventory-aware requirements, and terminal crafting.
- **Sophisticated series interface support**: Provide ingredient access, crafting slot mapping, grid filling, and crafting execution integration for supported crafting and storage interfaces.
- **GTM integration**: Read virtual circuit and additional ingredient information from recipes and project it into usable item or fluid inputs. Virtual circuits are represented by circuit items with the corresponding configuration, without adding duplicates when a circuit is already present.

## 8. Collapsible Ingredient Rules for the Right Sidebar

- **Rule-based ingredient grouping**: Place matching ingredients into expandable and collapsible groups to reduce the space occupied by similar items in the right sidebar.
- **Combined ingredient expressions**: Define grouping conditions using ingredient identifiers, tags, wildcards, components, and logical combinations.
- **Multiple coexisting rule files**: Read multiple `.txt` files from `config/jei/collapsible-items/`, allowing rules to be distributed by mod or modpack without combining them into one large file.
- **Priority and default rules**: Rule files with a bracketed prefix assign ingredients to groups first; remaining ingredients continue through ordinary rule matching. The default file has the same priority as other files without a prefix and is not disabled entirely when higher-priority rules exist.
- **Hot reloading and display configuration**: Reload rules when files are saved, added, or removed; preserve collapsed states; and configure expanded and collapsed colors in-game. Default rules are generated at startup only if the directory contains no `.txt` files.

## 9. Chat Sharing and Information Shortcuts

- **Ingredient chat sharing that preserves state**: Share items or fluids with their identifier, quantity, and component patch so the receiving client can reconstruct their state rather than simply look up a local entry by UID.
- **Specific recipe sharing**: Share a particular recipe through entry points such as the recipe bookmark button. Recipients can hover to preview its layout and open that specific recipe.
- **Bookmark group sharing and import**: Send bookmark groups as chat links that recipients can import into their own bookmarks.
- **Interactive pinned chat previews**: Pin a recipe or ingredient preview in chat, move the cursor into it to inspect ingredients, and perform supported lookup and copy operations.
- **Copy names and identifiers**: Copy names, ingredient IDs, recipe IDs, item tags, and item components from hovered targets for rule writing and communication.
- **Selective tag copying**: Open an interactive tag list and select the tag to copy.
- **Component rule copying**: Copy component expressions for use in rules. By default, only added or modified components are copied; a full mode that includes default components is configurable.
- **Crosshair target shortcuts**: Look up recipes or uses for the block under the crosshair, or add it to bookmarks directly from the world.

## 10. In-Game Configuration and Interface Improvements

- **Unified in-game configuration screen**: Open the built-in configuration screen through JEI's bottom-right button.
- **In-game key binding controls**: Display shortcuts by category, record bindings directly, indicate conflicts, and reset individual bindings using Minecraft's existing KeyMapping system.
- **Configurable configuration screen transparency**: Keep the previous screen visible as the background and allow its transparency to be adjusted.
- **Quantity field and layout coordination**: Show or hide the quantity field and change its position depending on whether the search bar is centered. Shortcut hints in tooltips and toast avoidance further improve interface usability.
- **Contextual operation hints**: Show ordinary actions and Ctrl-, Alt-, or other modifier-based actions in layers depending on whether the cursor is over an ingredient, recipe button, or bookmark group. This reduces the need to memorize shortcuts without placing every action in every tooltip at all times.

## Appendix: Implementation Index

| Section or feature | Implementation locations |
| --- | --- |
| Bookmarks and groups | `Gui/src/main/java/mezz/jei/gui/bookmarks/`; `Gui/src/main/java/mezz/jei/gui/overlay/bookmarks/` |
| Recipe chains and supply/demand | `Gui/src/main/java/mezz/jei/gui/bookmarks/chain/`; bookmark tooltips and inventory providers |
| Recipe trees | `Gui/src/main/java/mezz/jei/gui/bookmarks/tree/` |
| Favorites and preferences | `Gui/src/main/java/mezz/jei/gui/favorites/`; `favorites/preferences/` |
| Recipe search and selection | `Gui/src/main/java/mezz/jei/gui/recipes/filtering/`; `recipes/InputSlotSelectionState.java`; `Library/src/main/java/mezz/jei/library/gui/ingredients/RecipeSlotIngredients.java` |
| Execution and retrieval | `Gui/src/main/java/mezz/jei/gui/bookmarks/hotkeys/`; `Common/src/main/java/mezz/jei/common/bookmarks/` |
| External integrations | `Gui/src/main/java/mezz/jei/gui/compat/`; `NeoForge/src/main/java/mezz/jei/neoforge/compat/` |
| Collapsible rules | `Gui/src/main/java/mezz/jei/gui/collapsible/`; `gui/match/`; `gui/config/CollapsibleConfig.java` |
| Sharing and information shortcuts | `Common/src/main/java/mezz/jei/common/chat/`; `Gui/src/main/java/mezz/jei/gui/chat/`; `gui/input/handlers/` |
| Configuration and interface | `Gui/src/main/java/mezz/jei/gui/config/`; `gui/overlay/bookmarks/ScrollStepTextField.java` |
| Pinned bookmark recipe previews and transfer within previews | `Gui/src/main/java/mezz/jei/gui/overlay/bookmarks/BookmarkPreviewTooltipController.java`, `BookmarkPreviewTooltip.java` |
| Extended bookmark and favorite data persistence | `Gui/src/main/java/mezz/jei/gui/config/BookmarkJsonConfig.java`, `FavoriteRecipeConfig.java` |
| Multiple output targets and saved favorite inputs | `Gui/src/main/java/mezz/jei/gui/recipes/RecipeFavoriteButton.java`, `FavoriteRecipeTargetSelector.java` |
| Preference priorities, unique selection, and multiple-result filtering | `Gui/src/main/java/mezz/jei/gui/favorites/preferences/RecipePreferenceRules.java`, `gui/config/RecipePreferenceConfig.java` |
| Preference rule import by appending and hot reloading | `Gui/src/main/java/mezz/jei/gui/config/ConfigFileImporter.java`, `gui/startup/JeiGuiStarter.java` |
| Search extension integration | `Gui/src/main/java/mezz/jei/gui/recipes/filtering/RecipeLookupSnapshot.java`, `IRecipeSearchTextMatcher.java`; historical commit `b78bfa808` |
| GTM virtual circuits and ingredient projection | `Gui/src/main/java/mezz/jei/gui/compat/gtm/GtmVirtualCircuitCompat.java` |
| Layered operation hints | `Common/src/main/java/mezz/jei/common/gui/BookmarkHotkeyTooltipUtil.java` |
| Ghost ingredient target area refresh | `Gui/src/main/java/mezz/jei/gui/ghost/GhostIngredientDragManager.java` |
