import dayjs from 'dayjs/esm';

import { IHoliday, NewHoliday } from './holiday.model';

export const sampleWithRequiredData: IHoliday = {
  id: 1031,
  holidayName: 'Soft Corporate Engineer',
};

export const sampleWithPartialData: IHoliday = {
  id: 83516,
  holidayName: 'Lead radical Cook',
  day: 'Open-source Plastic',
  year: dayjs('2023-02-08T05:18'),
  lastModified: dayjs('2023-02-09T02:50'),
  lastModifiedBy: 'real-time Turkish multi-byte',
  status: 'neural primary',
};

export const sampleWithFullData: IHoliday = {
  id: 63282,
  holidayName: 'Exclusive',
  holidayDate: dayjs('2023-02-08T23:07'),
  day: 'Tools',
  year: dayjs('2023-02-09T03:22'),
  lastModified: dayjs('2023-02-08T05:16'),
  lastModifiedBy: 'networks',
  status: 'Arizona',
  companyId: 78879,
};

export const sampleWithNewData: NewHoliday = {
  holidayName: 'Crest artificial',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
