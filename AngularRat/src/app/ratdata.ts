import {LocationType} from "./locationtype";
import {Borough} from "./borough";

export class RatData {
    constructor (
        public locationType: LocationType,
        public zip: number,
        public address: string,
        public city: string,
        public borough: Borough,
        public latitude: number,
        public longitude: number,
        public id: number
    ) {}
}