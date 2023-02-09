import dayjs from 'dayjs/esm';

import { IWorkinDaysSetting, NewWorkinDaysSetting } from './workin-days-setting.model';

export const sampleWithRequiredData: IWorkinDaysSetting = {
  id: 79195,
};

export const sampleWithPartialData: IWorkinDaysSetting = {
  id: 30853,
  lastModifiedBy: 'capacitor',
  status: 'even-keeled Skyway Vermont',
  companyId: 22244,
};

export const sampleWithFullData: IWorkinDaysSetting = {
  id: 82038,
  lastModified: dayjs('2023-02-08T13:20'),
  lastModifiedBy: 'Fundamental Rustic',
  status: 'compressing Jersey optical',
  companyId: 74289,
};

export const sampleWithNewData: NewWorkinDaysSetting = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
