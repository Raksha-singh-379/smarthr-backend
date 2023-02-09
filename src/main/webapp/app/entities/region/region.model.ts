import dayjs from 'dayjs/esm';

export interface IRegion {
  id: number;
  regionName?: string | null;
  description?: string | null;
  status?: string | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  companyId?: number | null;
}

export type NewRegion = Omit<IRegion, 'id'> & { id: null };
