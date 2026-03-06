package mods.eln.init

import mods.eln.generic.GenericItemUsingDamageDescriptor
import mods.eln.item.TreeResin

/**
 * Central registry for shared items in Electrical Age.
 * These are damage-based items that share a single Item instance.
 */
object Items {
    // Multi-meter, thermometer, all-meter descriptors
    lateinit var multiMeterElement: GenericItemUsingDamageDescriptor

    lateinit var thermometerElement: GenericItemUsingDamageDescriptor

    lateinit var allMeterElement: GenericItemUsingDamageDescriptor

    // Tree resin item
    lateinit var treeResin: TreeResin
}
