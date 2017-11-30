import {LocationType} from "./locationtype";
import {Borough} from "./borough";

export class RatData {
    constructor (
        public locationType: string,
        public incidentZip: number,
        public incidentAddress: string,
        public city: string,
        public borough: string,
        public latitude: number,
        public longitude: number,
        public userId?: number,
        public id?: number,
        public createdDate?: Date,
    ) {}
}