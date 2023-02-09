import dayjs from 'dayjs/esm';

import { IDepartment, NewDepartment } from './department.model';

export const sampleWithRequiredData: IDepartment = {
  id: 39095,
};

export const sampleWithPartialData: IDepartment = {
  id: 8019,
  name: 'Cheese encryption Tuna',
  lastModifiedBy: 'Falls Morocco',
  status: 'Facilitator',
};

export const sampleWithFullData: IDepartment = {
  id: 74996,
  name: 'Dollar green',
  lastModified: dayjs('2023-02-08T07:47'),
  lastModifiedBy: 'Regional Avon',
  status: 'program Architect Bacon',
  companyId: 26441,
};

export const sampleWithNewData: NewDepartment = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
