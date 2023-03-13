import dayjs from 'dayjs/esm';

import { IAttendance, NewAttendance } from './attendance.model';

export const sampleWithRequiredData: IAttendance = {
  id: 67192,
};

export const sampleWithPartialData: IAttendance = {
  id: 12738,
  latitude: 73076,
  longitude: 66552,
  date: dayjs('2023-03-12T10:42'),
  companyId: 77614,
  status: 'haptic Ergonomic',
};

export const sampleWithFullData: IAttendance = {
  id: 31635,
  deviceInfo: 'Soap and',
  latitude: 37412,
  longitude: 60138,
  date: dayjs('2023-03-12T13:57'),
  day: 'data-warehouse well-modulated',
  hours: 'evolve Market',
  employeeId: 'yellow Fort Streamlined',
  companyId: 3171,
  status: 'Causeway',
  lastModified: dayjs('2023-03-12T10:12'),
  lastModifiedBy: 'Industrial interfaces',
};

export const sampleWithNewData: NewAttendance = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
