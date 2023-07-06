export interface Project {
  id : number;
  name: string;
  customer: string;
  groupId: number;
  members: null | string;
  status: Status;
  startDate: string;
  endDate: null | string;
  number: number;
  // selected: boolean;
  version: number;
}

export enum Status {
  NEW = 'NEW',
  PLA = 'PLA',
  INP = 'INP',
  FIN = 'FIN',
}
