import dayjs from 'dayjs/esm';

export interface IHoliday {
  id: number;
  holidayName?: string | null;
  holidayDate?: dayjs.Dayjs | null;
  day?: string | null;
  year?: dayjs.Dayjs | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  status?: string | null;
  companyId?: number | null;
}

export type NewHoliday = Omit<IHoliday, 'id'> & { id: null };
