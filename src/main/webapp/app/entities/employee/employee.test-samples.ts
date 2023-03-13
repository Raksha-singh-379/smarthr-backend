import dayjs from 'dayjs/esm';

import { IEmployee, NewEmployee } from './employee.model';

export const sampleWithRequiredData: IEmployee = {
  id: 7813,
  empUniqueId: 'transmitting',
};

export const sampleWithPartialData: IEmployee = {
  id: 8063,
  firstName: 'Brooke',
  lastName: 'Labadie',
  gender: 'empower e-tailers internet',
  empUniqueId: 'Implementation Pizza',
  joindate: dayjs('2023-02-08T21:09'),
  companyId: 71772,
  lastModified: dayjs('2023-02-08T17:44'),
};

export const sampleWithFullData: IEmployee = {
  id: 93602,
  firstName: 'Travis',
  middleName: 'world-class Berkshire Representative',
  lastName: 'Hermiston',
  gender: 'Rubber Fundamental Loan',
  empUniqueId: 'Tuna',
  joindate: dayjs('2023-02-08T19:40'),
  companyId: 44544,
  status: 'Rubber Forward',
  lastModified: dayjs('2023-02-08T18:21'),
  lastModifiedBy: 'Island Concrete Music',
  employmentTypeId: 45515,
};

export const sampleWithNewData: NewEmployee = {
  empUniqueId: 'Factors',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
