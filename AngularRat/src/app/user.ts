import {UserMode} from "./usermode";

export class User {
    constructor (
        public username: string,
        public password: string,
        public firstName?: string,
        public lastName?: string,
        public userMode?: UserMode
    ) {}
}