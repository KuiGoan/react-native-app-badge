declare module "react-native-app-badge" {

    interface ShortcutBadgeStatic {
        /**
         * Set the badge count. Use 0 to remove it.
         */
        setCount(count: number);
    }

    const ShortcutBadge: ShortcutBadgeStatic;

    export default ShortcutBadge;
}
