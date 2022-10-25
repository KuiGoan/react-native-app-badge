declare module "react-native-app-badge" {

    interface ShortcutBadgeStatic {
        /**
         * Launcher home name, null if unknown
         */
        readonly launcher: string | null;

        /**
         * If the launcher supported?
         */
        readonly supported: boolean;

        /**
         * Set the badge count. Use 0 to remove it.
         */
        setCount(count: number): Promise<boolean>;

        /**
         * Get the badge count.
         */
        getCount(): Promise<number>;
    }

    const ShortcutBadge: ShortcutBadgeStatic;

    export default ShortcutBadge;
}
