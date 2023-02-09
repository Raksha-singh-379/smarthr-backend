import dayjs from 'dayjs/esm';

import { IEmployee, NewEmployee } from './employee.model';

export const sampleWithRequiredData: IEmployee = {
  id: 7813,
  username: 'transmitting',
  password: 'scale Mexican Administrator',
  empUniqueId: 'Granite',
};

export const sampleWithPartialData: IEmployee = {
  id: 36931,
  username: 'SSL',
  password: 'View National Analyst',
  department: 'Representative user-centric',
  empUniqueId: 'open-source pixel',
  joindate: dayjs('2023-02-08T16:19'),
  lastModifiedBy: 'Tuna',
  branchId: 38021,
};

export const sampleWithFullData: IEmployee = {
  id: 44544,
  firstName: 'Gretchen',
  lastName: 'Cartwright',
  username: 'uniform Jamaica',
  password: 'payment exuding',
  department: 'Health',
  designation: 'Investor feed Chicken',
  gender: 'azure',
  empUniqueId: 'Progressive composite Account',
  joindate: dayjs('2023-02-08T12:10'),
  lastModified: dayjs('2023-02-08T06:37'),
  lastModifiedBy: 'XSS',
  status: 'Krone web Missouri',
  companyId: 17603,
  branchId: 90142,
  regionId: 96782,
};

export const sampleWithNewData: NewEmployee = {
  username: 'global XML',
  password: 'wireless',
  empUniqueId: 'Synergized bleeding-edge',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
